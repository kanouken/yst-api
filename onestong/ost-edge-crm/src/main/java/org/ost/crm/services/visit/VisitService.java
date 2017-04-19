package org.ost.crm.services.visit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.NotificationServiceClient;
import org.ost.crm.dao.visit.VisitApproverDao;
import org.ost.crm.dao.visit.VisitDao;
import org.ost.crm.dao.visit.VisitSupporterDao;
import org.ost.crm.model.common.CommonParams;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprovalFlow;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitApproverExample;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.VisitSupporterExample;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.model.visit.dto.UpdateVisitDto;
import org.ost.crm.model.visit.dto.VisitDetailDto;
import org.ost.crm.model.visit.dto.VisitListDto;
import org.ost.crm.model.visit.mapper.VisitEntityMapper;
import org.ost.crm.services.auth.AuthorityService;
import org.ost.crm.services.auth.UsersRole;
import org.ost.crm.services.base.BaseService;
import org.ost.crm.services.web.user.UserService;
import org.ost.entity.auth.Role;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.customer.Customer;
import org.ost.entity.notification.PushBody;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;
import org.ost.entity.user.mapper.UsersEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
public class VisitService extends BaseService {

	public static final String NEW_VISIT = "您有一条外访申请需要审批，请尽快审批";

	/**
	 * 角色 审批 审批
	 */
	public static final Byte ROLE_APPROVAL_APPROVA = 0;
	/**
	 * 角色 审批 观察
	 */
	public static final Byte ROLE_APPROVAL_VIEW = 1;

	/**
	 * 角色 人员 发起人
	 */
	public static final Byte ROLE_VISIT_CREATOR = 0;
	/**
	 * 角色 人员 支持者
	 */
	public static final Byte ROLE_VISIT_SUPPORT = 1;

	@Autowired
	VisitDao visitDao;

	@Autowired
	ContactsServiceClient contactsServiceClient;

	@Autowired
	VisitApproverDao visitApproverDao;

	@Autowired
	VisitSupporterDao visitSupportDao;

	@Autowired
	UserService userService;

	@Autowired
	NotificationServiceClient notificationServiceClient;

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
		if (StringUtils.isEmpty(createVisitDto.getProjectTypeID())) {
			createVisitDto.setProjectTypeID(null);
		}
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
		List<VisitSupporter> vps = new ArrayList<VisitSupporter>();
		// 支持人员
		if (CollectionUtils.isNotEmpty(createVisitDto.getSupportUsers())) {

			createVisitDto.getSupportUsers().forEach(p -> {
				vps.add(VisitEntityMapper.INSTANCE.combineSupporterDtoAndVisitToVisitSupporter(p, visit));
			});

		}
		// 发起人
		VisitSupporter vs = new VisitSupporter(visit.getId(), currentUser.getUserId(), currentUser.getRealname(),
				currentUser.getDeptId(), currentUser.getDepartmentName(), Byte.parseByte("0"));
		if (createVisitDto.getVisitContent() != null)
			vs.setVisitContent(mapper.writeValueAsString(createVisitDto.getVisitContent()));
		vs.setVisitDetail(createVisitDto.getVisitDetail());
		vs.setCreateBy(currentUser.getRealname());
		vs.setUpdateBy(currentUser.getRealname());
		vs.setCreateTime(visit.getCreateTime());
		vs.setUpdateTime(visit.getUpdateTime());
		vs.setCreateId(visit.getCreateId());
		vs.setUpdateId(visit.getUpdateId());
		vs.setSchemaId(visit.getSchemaId());
		vps.add(vs);
		visitDao.insertVisitSupporter(vps);
		List<VisitApprover> vas = new ArrayList<VisitApprover>();
		// 审批人
		if (CollectionUtils.isNotEmpty(createVisitDto.getApprovalUsers())) {

			createVisitDto.getApprovalUsers().forEach(p -> {
				vas.add(VisitEntityMapper.INSTANCE.combineUserListDtoAndVisitToVisitApprover(p, visit));
			});
			// 观察者 行政
			Role role = new Role();
			role.setDomainId(currentUser.getSchemaId());
			role.setRoleCode(UsersRole.HR.getCode());
			List<Users> hr = this.authorityService.findUsersByRole(role);
			if (CollectionUtils.isNotEmpty(hr)) {
				List<UserListDto> hrDtos = UsersEntityMapper.INSTANCE.usersToUserListDto(hr);
				hrDtos.forEach(p -> {
					VisitApprover vApprover = VisitEntityMapper.INSTANCE.combineUserListDtoAndVisitToVisitApprover(p,
							visit);
					vApprover.setRole(ROLE_APPROVAL_VIEW);
					vas.add(vApprover);
				});
			}
			visitDao.insertVisitApprover(vas);
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
		// 推送审批人员消息
		if (CollectionUtils.isNotEmpty(vas)) {
			// send notification
			CompletableFuture.runAsync(() -> {
				List<Integer> approverIds = vas.stream().map(u -> Integer.valueOf(u.getUserID()))
						.collect(Collectors.toList());
				List<Users> approveruers = userService.findUsersByIds(approverIds);
				if (CollectionUtils.isNotEmpty(approveruers)) {
					PushBody pb = new PushBody();
					pb.setApplication("onestong-crm");
					pb.setCids(approveruers.stream().map(u -> u.getcId()).collect(Collectors.toList()));
					pb.setContent("");
					String payLoad = NEW_VISIT;
					pb.setPayLoadBody(payLoad);
					notificationServiceClient.pushBatch(pb);
				}
			});
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

	@Autowired
	VisitAttenceService visitAttenceService;

	/**
	 * 
	 * @param currentUser
	 * @param id
	 * @param actionType
	 *            2 外访发起人 2.1支持者 2.2 观察 2.3 审批
	 * 
	 *            20170331 将考勤记录、支持人员、外访内容列表
	 * @return
	 */
	@Transactional(readOnly = true)
	public VisitDetailDto queryDetail(Users currentUser, Integer id, String actionType) {
		VisitDetailDto visitDetailDto = null;
		Visit visit = visitDao.selectByPrimaryKey(id);
		Assert.notNull(visit, "参数异常");
		VisitSupporter vsSupporter = null;
		if (actionType.trim().equals("2")) {
			// 发起人
			vsSupporter = new VisitSupporter();
			vsSupporter.setUserID(currentUser.getUserId());
			vsSupporter.setVisitEventID(id);
			vsSupporter.setRole(ROLE_VISIT_CREATOR);
			vsSupporter = visitSupportDao.selectOne(vsSupporter);
			visit.setCreateId(vsSupporter.getUserID());
			Assert.notNull(vsSupporter, "参数错误");
			visitDetailDto = VisitEntityMapper.INSTANCE.combineVisitAndSupporterToVisitDetailDto(visit, vsSupporter);
			// 考勤
			if (vsSupporter.getAttenceEventID() != null) {
				visitDetailDto.setAttence(visitAttenceService.queryAttenceBySupport(vsSupporter));
			}
			// 支持人员
			visitDetailDto.setSupporters(supporters(currentUser.getSchemaId(), id));
			// 外访内容参数
			List<CommonParams> params = getParamsEx("visit_event_content_creator");
			visitDetailDto.setAllVisitContent(params.stream().map(cp -> cp.getVal()).collect(Collectors.toList()));
		} else if (actionType.trim().equals("2.1")) {
			// 支持者
			vsSupporter = new VisitSupporter();
			vsSupporter.setVisitEventID(id);
			List<VisitSupporter> vsSupporters = visitSupportDao.select(vsSupporter);
			// 创建者
			Map<Byte, List<VisitSupporter>> roleGroup = vsSupporters.stream()
					.collect(Collectors.groupingBy(VisitSupporter::getRole));
			List<VisitSupporter> supporters = (List<VisitSupporter>) MapUtils.getObject(roleGroup, ROLE_VISIT_SUPPORT);
			Optional<VisitSupporter> optional = supporters.stream()
					.filter(s -> s.getUserID().equals(currentUser.getUserId())).findFirst();
			if (optional.isPresent()) {
				vsSupporter = optional.get();
			}
			List<VisitSupporter> creators = (List<VisitSupporter>) MapUtils.getObject(roleGroup, ROLE_VISIT_CREATOR);
			visit.setCreateId(creators.get(0).getUserID());
			visitDetailDto = VisitEntityMapper.INSTANCE.combineVisitAndSupporterToVisitDetailDto(visit, vsSupporter);
			// 考勤
			if (vsSupporter.getAttenceEventID() != null) {
				visitDetailDto.setAttence(visitAttenceService.queryAttenceBySupport(vsSupporter));
			}
			// 外访内容参数
			List<CommonParams> params = getParamsEx("visit_event_content_supporter");
			visitDetailDto.setAllVisitContent(params.stream().map(cp -> cp.getVal()).collect(Collectors.toList()));
		} else if (actionType.trim().equals("2.2")) {
			// 外访观察者 FIXME 发起人只有一个
			vsSupporter = new VisitSupporter();
			vsSupporter.setVisitEventID(id);
			vsSupporter.setRole(ROLE_VISIT_CREATOR);
			vsSupporter = visitSupportDao.selectOne(vsSupporter);
			Assert.notNull(vsSupporter, "参数错误");
			visit.setCreateId(vsSupporter.getId());
			visitDetailDto = VisitEntityMapper.INSTANCE.combineVisitAndSupporterToVisitDetailDto(visit, vsSupporter);
			// 考勤
			if (vsSupporter.getAttenceEventID() != null) {
				visitDetailDto.setAttence(visitAttenceService.queryAttenceBySupport(vsSupporter));
			}
			// 支持人员
			visitDetailDto.setSupporters(supporters(currentUser.getSchemaId(), id));
			// 外访内容参数
			List<CommonParams> params = getParamsEx("visit_event_content_creator");
			visitDetailDto.setAllVisitContent(params.stream().map(cp -> cp.getVal()).collect(Collectors.toList()));
		} else if (actionType.trim().equals("2.3")) {
			// 外访审批
			vsSupporter = new VisitSupporter();
			vsSupporter.setVisitEventID(id);
			vsSupporter.setRole(ROLE_VISIT_CREATOR);
			vsSupporter = visitSupportDao.selectOne(vsSupporter);
			Assert.notNull(vsSupporter, "参数错误");
			visit.setCreateId(vsSupporter.getUserID());
			visitDetailDto = VisitEntityMapper.INSTANCE.combineVisitAndSupporterToVisitDetailDto(visit, vsSupporter);
			// 考勤
			if (vsSupporter.getAttenceEventID() != null) {
				visitDetailDto.setAttence(visitAttenceService.queryAttenceBySupport(vsSupporter));
			}
			// 支持人员
			visitDetailDto.setSupporters(supporters(currentUser.getSchemaId(), id));
			// 外访内容参数
			List<CommonParams> params = getParamsEx("visit_event_content_creator");
			visitDetailDto.setAllVisitContent(params.stream().map(cp -> cp.getVal()).collect(Collectors.toList()));
			// 审批状态
			VisitApprover vApprover = new VisitApprover();
			vApprover.setUserID(currentUser.getUserId());
			vApprover.setVisitEventID(id);
			vApprover.setRole(ROLE_APPROVAL_APPROVA);
			vApprover = visitApproverDao.selectOne(vApprover);
			visitDetailDto.setRoleApprovalStatus(vApprover.getApprovalStatus().toString());
		} else {
			throw new IllegalArgumentException("参数异常");
		}

		return visitDetailDto;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> querySupporters(Users currentUser, Integer id) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("objects", supporters(currentUser.getSchemaId(), id));
		return result;
	}

	@Transactional(readOnly = true)
	public List<UserListDto> supporters(String schemaId, Integer visitId) {
		List<UserListDto> supporters = this.visitSupportDao.selectByVisitId(schemaId, visitId,
				ROLE_VISIT_SUPPORT.intValue());
		return supporters;
	}

	/**
	 * 更新外访
	 * 
	 * @param currentUser
	 * @param updateVisitDto
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateVisit(Users currentUser, UpdateVisitDto updateVisitDto, Integer id)
			throws JsonProcessingException {
		// role check

		VisitSupporter vSupporter = new VisitSupporter();
		vSupporter.setUserID(currentUser.getUserId());
		vSupporter.setVisitEventID(id);
		vSupporter.setRole(ROLE_VISIT_CREATOR);
		VisitSupporter creator = visitSupportDao.selectOne(vSupporter);

		// update contacts
		if (CollectionUtils.isNotEmpty(updateVisitDto.getContacts())) {
			Assert.notNull(creator, "参数异常");
			VisitContactsDto vContactsDto = new VisitContactsDto();
			vContactsDto.setVisitEventId(id);
			vContactsDto.setContactsIds(
					updateVisitDto.getContacts().stream().map(c -> c.getId()).collect(Collectors.toList()));
			vContactsDto.setUserId(String.valueOf(currentUser.getUserId()));
			vContactsDto.setUserName(currentUser.getRealname());
			OperateResult<String> result = this.contactsServiceClient.updateVisitContacts(currentUser.getSchemaId(),
					vContactsDto);
			if (result.success()) {
			} else {
				throw new ApiException("更新外访失败", result.getInnerException());
			}
		}
		// 项目
		if (CollectionUtils.isNotEmpty(updateVisitDto.getProjects())) {
			Assert.notNull(creator, "参数异常");
			visitDao.deleteVisitProject(id);
			List<VisitProject> vps = new ArrayList<VisitProject>();
			Visit visit = this.visitDao.selectByPrimaryKey(id);
			visit.setCreateTime(new Date());
			visit.setUpdateTime(new Date());
			updateVisitDto.getProjects().forEach(p -> {
				vps.add(VisitEntityMapper.INSTANCE.combineProjectListDtoAndVisitToVisitProject(p, visit));
			});
			visitDao.insertVisitProject(vps);
		}

		if (StringUtils.isNotEmpty(updateVisitDto.getProjectTypeID())) {
			Assert.notNull(creator, "参数异常");
			Visit visit = new Visit();
			visit.setId(id);
			visit.setUpdateBy(currentUser.getRealname());
			visit.setUpdateTime(new Date());
			visit.setProjectTypeID(Integer.parseInt(updateVisitDto.getProjectTypeID()));
			this.visitDao.updateByPrimaryKeySelective(visit);
		}

		if (updateVisitDto.getVisitContent() != null || updateVisitDto.getVisitDetail() != null) {
			vSupporter = new VisitSupporter();
			vSupporter.setUpdateBy(currentUser.getRealname());
			vSupporter.setUpdateTime(new Date());
			if (updateVisitDto.getVisitContent() != null)
				vSupporter.setVisitContent(this.objctMapper.writeValueAsString(updateVisitDto.getVisitContent()));
			vSupporter.setVisitDetail(updateVisitDto.getVisitDetail());
			VisitSupporterExample vse = new VisitSupporterExample();
			vse.createCriteria().andVisiteventidEqualTo(id).andUseridEqualTo(currentUser.getUserId());
			if (0 == visitSupportDao.updateByExampleSelective(vSupporter, vse)) {
				throw new ApiException("更新失败", "");
			}
		}
		return HttpStatus.OK.name();
	}

	@Transactional(readOnly = true)
	public Map<String, Object> queryByCustomer(Customer customer, String contactDate, String contactType,
			String createBy, Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contactDate", contactDate);
		params.put("createBy", createBy);
		params.put("contactType", contactType);
		List<VisitListDto> visitListDtos = new ArrayList<VisitListDto>();
		RowBounds rBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());
		Integer totalRecords = visitDao.selectCountByCustomer(customer, params);

		if (totalRecords != null && totalRecords > 0) {
			visitListDtos = this.visitDao.selectByCustomer(customer, params, rBounds);
			// 获取关联联系人
			int[] visitIds = visitListDtos.stream().mapToInt(r -> Integer.valueOf(r.getId())).toArray();
			OperateResult<List<VisitContactsDto>> contactsOperateResult = this.contactsServiceClient
					.queryByVisits(customer.getSchemaId(), visitIds);
			if (contactsOperateResult.success()) {
				List<VisitContactsDto> vistContactsDtos = contactsOperateResult.getData();
				visitListDtos.forEach(r -> {
					Optional<VisitContactsDto> _r = vistContactsDtos.stream()
							.filter(vc -> vc.getVisitEventId().toString().equals(r.getId())).findFirst();
					if (_r.isPresent()) {
						r.setContact(
								_r.get().getContacts().stream().map(c -> c.getName()).collect(Collectors.joining(",")));
					}
				});
			} else {
				throw new ApiException("获取外访联系人失败", contactsOperateResult.getInnerException());
			}

		}

		page.setTotalRecords(totalRecords);
		return OperateResult.renderPage(page, visitListDtos);

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> queryMyVisit(Users currentUser, Page page) {
		List<Users> users = this.userService.findUserScopes(currentUser);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userScope", users);
		params.put("userId", currentUser.getUserId());
		Date  start = null,end = null;
		Calendar c  = Calendar.getInstance();
		c.setTime(new Date());
		end  = c.getTime();
		end = DateUtil.setDayMaxTime(end);
		c.add(Calendar.DAY_OF_WEEK,-7);
		start = c.getTime();
		start = DateUtil.setDayMinTime(start);
		params.put("startTime",start);
		params.put("endTime",end);
		RowBounds rBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());
		return this.visitDao.selectMyVisit(params, rBounds);
	}

}
