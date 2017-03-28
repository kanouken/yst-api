package org.ost.crm.services.visit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.dao.visit.VisitApproverDao;
import org.ost.crm.dao.visit.VisitDao;
import org.ost.crm.model.common.CommonParams;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprovalFlow;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitApproverExample;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.model.visit.dto.VisitAttenceCreateDto;
import org.ost.crm.model.visit.mapper.VisitEntityMapper;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VisitService extends BaseService {
	/**
	 * 角色 审批 审批
	 */
	public static final Byte ROLE_APPROVAL_APPROVA = 0;
	/**
	 * 角色 审批 观察
	 */
	public static final Byte ROLE_APPROVAL_VIEW = 1;

	@Autowired
	VisitDao visitDao;

	@Autowired
	ContactsServiceClient contactsServiceClient;

	@Autowired
	VisitApproverDao visitApproverDao;

	/**
	 * 新增外访
	 * 
	 * @param currentUser
	 * @param createVisitDto
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createVisit(Users currentUser, CreateVisitDto createVisitDto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Visit visit = VisitEntityMapper.INSTANCE.combineCreateVisitDtoAndUsersToVisit(createVisitDto, currentUser);
		visitDao.insertSelective(visit);
		// 项目
		if (CollectionUtils.isNotEmpty(createVisitDto.getProjects())) {
			List<VisitProject> vps = new ArrayList<VisitProject>();
			createVisitDto.getProjects().forEach(p -> {
				vps.add(VisitEntityMapper.INSTANCE.combineProjectListDtoAndVisitToVisitProject(p, visit));
			});
			visitDao.insertVisitProject(vps);
		}
		// 支持人员
		if (CollectionUtils.isNotEmpty(createVisitDto.getSupportUsers())) {
			List<VisitSupporter> vps = new ArrayList<VisitSupporter>();
			createVisitDto.getSupportUsers().forEach(p -> {
				vps.add(VisitEntityMapper.INSTANCE.combineSupporterDtoAndVisitToVisitSupporter(p, visit));
			});
			// 发起人
			VisitSupporter vs = new VisitSupporter(visit.getId(), currentUser.getUserId(), currentUser.getRealname(),
					currentUser.getDeptId(), currentUser.getDepartmentName(), Byte.parseByte("0"));
			vs.setVisitContent(mapper.writeValueAsString(createVisitDto.getVisitContent()));
			vs.setVisitDetail(createVisitDto.getVisitDetail());
			vs.setCreateTime(visit.getCreateTime());
			vs.setUpdateTime(visit.getUpdateTime());
			vs.setCreateId(visit.getCreateId());
			vs.setUpdateId(visit.getUpdateId());
			vs.setSchemaId(visit.getSchemaId());
			vps.add(vs);
			visitDao.insertVisitSupporter(vps);
		}

		// 审批人
		if (CollectionUtils.isNotEmpty(createVisitDto.getApprovalUsers())) {
			List<VisitApprover> vps = new ArrayList<VisitApprover>();
			createVisitDto.getApprovalUsers().forEach(p -> {
				vps.add(VisitEntityMapper.INSTANCE.combineUserListDtoAndVisitToVisitApprover(p, visit));
			});
			visitDao.insertVisitApprover(vps);
			// TODO 观察者 行政
		}
		// 联系人
		if (CollectionUtils.isNotEmpty(createVisitDto.getContacts())) {
			VisitContactsDto visitContactsDto = new VisitContactsDto();
			visitContactsDto.setVisitEventId(visit.getId());
			visitContactsDto.setUserId(String.valueOf(currentUser.getUserId()));
			visitContactsDto.setUserName(currentUser.getRealname());
			visitContactsDto.setContactsIds(
					createVisitDto.getContacts().stream().map(c -> c.getId()).collect(Collectors.toList()));
			OperateResult<String> result = contactsServiceClient.createVisitContacts(currentUser.getSchemaId(),
					visitContactsDto);
			if (!result.success()) {
				throw new ApiException("新增外访失败", result.getInnerException());
			}
		}
		return HttpStatus.OK.name();
	}

	/**
	 * 外访内容参数
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@Transactional(readOnly = true)
	public Map<String, List> queryVisitAttrs() {
		List<CommonParams> params = this.getParamsEx("visit_event_content");
		Map<String, List> result = new HashMap<String, List>();
		List<Map<String, String>> contentLists = new ArrayList<Map<String, String>>();
		if (CollectionUtils.isNotEmpty(params)) {
			params.forEach(p -> {
				contentLists.add(new HashMap<String, String>() {
					{
						put("type", p.getType());
						put("val", p.getVal());
					}
				});
			});
		}
		result.put("contentList", contentLists);
		return result;
	}

	/**
	 * 外访审批
	 * 
	 * @param currentUser
	 *            当前操作用户
	 * @param visitId
	 *            外访id
	 * @param approvalStatus
	 *            审批状态 -1:审批未通过 0:审批未完成 1:审批通过
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String doApproval(Users currentUser, Integer visitId, Byte approvalStatus) {
		VisitApprover vApprover = new VisitApprover();
		vApprover.setVisitEventID(visitId);
		vApprover.setUpdateTime(new Date());
		vApprover.setUpdateBy(currentUser.getRealname());
		// TODO check valid status
		vApprover.setApprovalStatus(approvalStatus);
		VisitApproverExample vae = new VisitApproverExample();
		vae.createCriteria().andSchemaidEqualTo(currentUser.getSchemaId()).andVisiteventidEqualTo(visitId)
				.andUseridEqualTo(currentUser.getUserId());
		if (0 < visitApproverDao.updateByExampleSelective(vApprover, vae)) {
			// 检查是否全部审批通过
			vApprover = new VisitApprover();
			vApprover.setVisitEventID(visitId);
			vApprover.setSchemaId(currentUser.getSchemaId());
			vApprover.setRole(ROLE_APPROVAL_APPROVA);
			vApprover.setIsDelete(NO);
			List<VisitApprover> visitApprovers = this.visitApproverDao.select(vApprover);
			if (VisitApprovalFlow.PASSED.getState().intValue() == visitApprovers.stream()
					.mapToInt(va -> va.getApprovalStatus().intValue()).reduce(1, (a, b) -> a * b)) {
				// 通过
				Visit visit = new Visit();
				visit.setId(visitId);
				visit.setUpdateBy(currentUser.getRealname());
				visit.setUpdateTime(new Date());
				visit.setApprovalStatus(VisitApprovalFlow.PASSED.getState());
				this.visitDao.updateByPrimaryKeySelective(visit);
			} else {
				// 不通过
				Visit visit = new Visit();
				visit.setId(visitId);
				visit.setUpdateBy(currentUser.getRealname());
				visit.setUpdateTime(new Date());
				visit.setApprovalStatus(VisitApprovalFlow.NO_PASS.getState());
				this.visitDao.updateByPrimaryKeySelective(visit);
			}
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("审批失败", "未找到审批数据");
		}
	}

}
