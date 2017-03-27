package org.ost.crm.services.visit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.dao.visit.VisitDao;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.dto.CreateVisitDto;
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
import com.sun.tools.classfile.StackMapTable_attribute.verification_type_info;

@Service
public class VisitService extends BaseService {
	@Autowired
	VisitDao visitDao;

	@Autowired
	ContactsServiceClient contactsServiceClient;

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

}
