package org.ost.crm.services.visit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.common.tools.exception.ApiException;
import org.ost.crm.dao.visit.VisitAttenceDao;
import org.ost.crm.dao.visit.VisitSupporterDao;
import org.ost.crm.model.visit.VisitAttence;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.VisitSupporterExample;
import org.ost.crm.model.visit.dto.VisitAttenceCreateDto;
import org.ost.crm.model.visit.dto.VisitAttenceDto;
import org.ost.crm.model.visit.mapper.VisitEntityMapper;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.notification.visit.UnSignOutVisit;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class VisitAttenceService extends BaseService {
	/**
	 * "考勤状态\n1:没有签退\n2:签退 但是 没有签到\n3:既签到又签退"
	 */
	public static final Byte ATTENCE_STATUS_SIGN_IN_NO_OUT = 1;

	public static final Byte ATTENCE_STATUS_SIGN_OUT_NO_IN = 2;

	public static final Byte ATTENCE_STATUS_SIGN_IN_OUT = 3;
	/**
	 * signInState "签到状态\n0:未签到\n1:已签到"
	 */
	public static final Byte ATTENCE_SIGN_IN = 1;
	public static final Byte ATTENCE_NO_SIGN_IN = 0;

	/**
	 * "签退状态\n0:未签退\n1:已签退"
	 */
	public static final Byte ATTENCE_SIGN_OUT = 1;
	public static final Byte ATTENCE_NO_SIGN_OUT = 0;

	public static final String ATTENCE_TYPE_GPS = "gps";
	public static final String ATTENCE_TYPE_IBEACON = "ibeacon";

	@Autowired
	private VisitAttenceDao visitAttenceDao;
	@Autowired
	private VisitSupporterDao visitSupporterDao;

	/**
	 * 
	 * @param currentUser
	 * @param visitId
	 * @param visitAttenceCreateDto
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String signIn(Users currentUser, Integer visitId, VisitAttenceCreateDto visitAttenceCreateDto) {

		// check
		VisitSupporter vSupporter = new VisitSupporter();
		vSupporter.setVisitEventID(visitId);
		vSupporter.setIsDelete(NO);
		vSupporter.setSchemaId(currentUser.getSchemaId());
		vSupporter.setUserID(currentUser.getUserId());
		vSupporter = visitSupporterDao.selectOne(vSupporter);
		// FIXME 关联考勤表查询
		if (vSupporter.getAttenceEventID() != null) {
			throw new ApiException("外访考勤已签到", "");
		}
		String attenceId = UUID.randomUUID().toString();
		VisitAttence visitAttence = VisitEntityMapper.INSTANCE
				.combineCreateVisitAttenceAndCurrentUserToVisitAttence(visitAttenceCreateDto, currentUser);
		visitAttence.setState(ATTENCE_STATUS_SIGN_IN_NO_OUT);
		visitAttence.setId(attenceId);
		visitAttence.setUserID(currentUser.getUserId());
		visitAttence.setUserName(currentUser.getRealname());
		visitAttence.setOrganizeID(currentUser.getDeptId());
		visitAttence.setOrganizeName(currentUser.getDepartmentName());
		visitAttence.setSignInState(ATTENCE_SIGN_IN);
		visitAttence.setSignInType(ATTENCE_TYPE_GPS);
		visitAttence.setSignInTime(new Date());
		visitAttenceDao.insertSelective(visitAttence);
		// update
		VisitSupporter visitSupporter = new VisitSupporter();
		visitSupporter.setVisitEventID(visitId);
		visitSupporter.setUpdateTime(new Date());
		visitSupporter.setUpdateBy(currentUser.getRealname());
		visitSupporter.setAttenceEventID(attenceId);
		VisitSupporterExample vse = new VisitSupporterExample();
		vse.createCriteria().andVisiteventidEqualTo(visitId).andSchemaidEqualTo(currentUser.getSchemaId())
				.andUseridEqualTo(currentUser.getUserId()).andIsdeleteEqualTo(Byte.parseByte("0"));
		this.visitSupporterDao.updateByExampleSelective(visitSupporter, vse);
		return HttpStatus.OK.name();
	}

	/**
	 * 
	 * @param currentUser
	 * @param visitId
	 * @param visitAttenceCreateDto
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String signOut(Users currentUser, Integer visitId, VisitAttenceCreateDto visitAttenceCreateDto) {
		// check
		VisitAttence visitAttence = visitAttenceDao.selectAttence(visitId, currentUser.getUserId(),
				currentUser.getSchemaId());
		if (visitAttence == null) {
			throw new ApiException("无考勤记录", "");
		}
		if (visitAttence.getSignOutState() != null && visitAttence.getSignOutState().equals(ATTENCE_SIGN_OUT)) {
			throw new ApiException("外访考勤已签退", "");
		}
		String attenceId = visitAttence.getId();
		visitAttence = VisitEntityMapper.INSTANCE
				.combineCreateVisitAttenceAndCurrentUserToVisitAttence(visitAttenceCreateDto, currentUser);
		visitAttence.setState(ATTENCE_STATUS_SIGN_IN_OUT);
		visitAttence.setSignOutState(ATTENCE_SIGN_OUT);
		visitAttence.setSignOutTime(new Date());
		visitAttence.setSignOutType(ATTENCE_TYPE_GPS);
		visitAttence.setId(attenceId);
		visitAttence.setCreateTime(null);
		visitAttenceDao.updateByPrimaryKeySelective(visitAttence);
		return HttpStatus.OK.name();
	}

	@Transactional(readOnly = true)
	public VisitAttenceDto queryAttence(Users currentUser, Integer id) {

		VisitSupporter visitSupporter = new VisitSupporter();
		visitSupporter.setVisitEventID(id);
		visitSupporter.setUserID(currentUser.getUserId());
		visitSupporter = visitSupporterDao.selectOne(visitSupporter);
		Assert.notNull(visitSupporter, "参数错误");
		VisitAttence visitAttence = visitAttenceDao.selectByPrimaryKey(visitSupporter.getAttenceEventID());
		VisitAttenceDto visitAttenceDto = VisitEntityMapper.INSTANCE.visitAttenceToVisitAttenceDto(visitAttence);
		return visitAttenceDto;
	}

	/**
	 * 查询当前时间未签退的外访记录
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<UnSignOutVisit> queryNoSignOut() {
		return this.visitAttenceDao.selectUnSignOut();
	}
}
