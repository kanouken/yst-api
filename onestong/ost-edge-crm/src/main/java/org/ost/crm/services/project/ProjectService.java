package org.ost.crm.services.project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.dao.project.CustomerProjectDao;
import org.ost.crm.dao.project.ProjectDao;
import org.ost.crm.dao.project.ProjectFileDao;
import org.ost.crm.dao.project.ProjectOrgDao;
import org.ost.crm.dao.project.ProjectPaymentDao;
import org.ost.crm.dao.project.ProjectPaymentExample;
import org.ost.crm.dao.project.ProjectStepDao;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.crm.dao.project.ProjectTypeStepDao;
import org.ost.crm.dao.project.UserProjectDao;
import org.ost.crm.model.common.CommonParams;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.customer.CustomerProject;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectOrg;
import org.ost.entity.project.ProjectPayment;
import org.ost.entity.project.ProjectState;
import org.ost.entity.project.ProjectStep;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.UserProject;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectDetailDto;
import org.ost.entity.project.dto.ProjectListDto;
import org.ost.entity.project.dto.ProjectListDto.CurrentStep;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.project.dto.ProjectStepsDto;
import org.ost.entity.project.mapper.ProjectEntityMapper;
import org.ost.entity.project.vo.ProjectVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService extends BaseService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ProjectFileDao projectFileDao;

	@Autowired
	private CustomerProjectDao projectCustomerDao;

	@Autowired
	private ProjectStepDao projectStepDao;
	@Autowired
	private ContactsServiceClient contactsServiceClient;

	@Autowired
	private CustomerServiceClient customerServiceClient;
	@Autowired
	private ProjectOrgDao poDao;

	@Autowired
	private UserProjectDao userProjectDao;

	@Autowired
	private ProjectPaymentDao ppDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createProject(Users user, ProjectCreateOrUpdateDto dto) {
		if(StringUtils.isEmpty(dto.getStartTimeStr())){
			dto.setStartTimeStr(null);
		}
		Project project = ProjectEntityMapper.INSTANCE.createOrUpateDtoToProject(dto);
		project.setCreateBy(user.getRealname());
		project.setUpdateBy(user.getRealname());
		project.setSchemaId(user.getSchemaId());
		project.setCreateTime(new Date());
		project.setUpdateTime(new Date());
		if (dto.getStartTimeStr() != null) {
			project.setState(ProjectState.LONG_ATT.getState());
		} else {
			project.setState(ProjectState.NORMAL.getState());
		}
		projectDao.insertSelective(project);
		if (CollectionUtils.isNotEmpty(dto.getContacts())) {
			ProjectContactsDto projectContactsDto = new ProjectContactsDto();
			projectContactsDto.setProject(new ProjectVo(project.getId(), project.getName()));
			projectContactsDto
					.setContacts(ContactsEntityMapper.INSTANCE.contactsDtoToContactsListDto(dto.getContacts()));
			projectContactsDto.setUserName(user.getRealname());
			OperateResult<String> result = this.contactsServiceClient.updateContactProject(user.getSchemaId(),
					projectContactsDto);
			if (!result.success()) {
				throw new ApiException("新建项目失败", result.getInnerException());
			}
		}
		// project customer

		if (dto.getCustomer() != null) {
			CustomerProject cProject = new CustomerProject();
			cProject.setProjectID(project.getId());
			cProject.setCustomerID(dto.getCustomer().getId());
			cProject.setCreateBy(project.getCreateBy());
			cProject.setUpdateBy(project.getUpdateBy());
			cProject.setCreateTime(project.getCreateTime());
			cProject.setUpdateTime(project.getUpdateTime());
			cProject.setSchemaId(project.getSchemaId());
			projectCustomerDao.insertSelective(cProject);
		}

		dto.getDeptOwner().forEach(dept -> {
			ProjectOrg pOrg = new ProjectOrg();
			pOrg.setOrganizeID(dept.getId());
			pOrg.setProjectID(project.getId());
			pOrg.setCreateBy(project.getCreateBy());
			pOrg.setUpdateBy(project.getUpdateBy());
			pOrg.setCreateTime(new Date());
			pOrg.setUpdateTime(new Date());
			pOrg.setSchemaId(user.getSchemaId());
			pOrg.setOrganizeName(dept.getName());
			poDao.insertSelective(pOrg);
		});
		//
		dto.getManagerOwner().forEach(users -> {
			UserProject uProject = new UserProject();
			uProject.setUserID(users.getId());
			uProject.setProjectID(project.getId());
			uProject.setCreateBy(project.getCreateBy());
			uProject.setUpdateBy(project.getUpdateBy());
			uProject.setCreateTime(new Date());
			uProject.setUpdateTime(new Date());
			uProject.setSchemaId(user.getSchemaId());
			uProject.setUserName(users.getName());
			uProject.setOrganizeID(users.getDeptID());
			uProject.setOrganizeName(users.getDeptName());
			userProjectDao.insertSelective(uProject);
		});

		return HttpStatus.OK.name();
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteProject(Integer projectId, Users user) {
		Project project = new Project();
		project.setId(projectId);
		project.setUpdateBy(user.getRealname());
		project.setUpdateTime(new Date());
		project.setSchemaId(user.getSchemaId());
		project.setIsDelete(Short.parseShort("1"));
		this.projectDao.updateByPrimaryKeySelective(project);
		return "ok";
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProjectPayment(Users users, Integer projectId, List<ProjectPaymentDto> dtos) {
		ProjectPaymentExample paymentExample = new ProjectPaymentExample();
		paymentExample.createCriteria().andSchemaidEqualTo(users.getSchemaId()).andProjectidEqualTo(projectId);
		ProjectPayment pp = new ProjectPayment();
		pp.setIsDelete(Short.parseShort("1"));
		ppDao.updateByExampleSelective(pp, paymentExample);

		dtos.forEach(ppdto -> {
			if (StringUtils.isEmpty(ppdto.getTimeStr())) {
				ppdto.setTimeStr(null);
			}
			ProjectPayment _pp = ProjectEntityMapper.INSTANCE.projectPaymentDtoToProjectPayment(ppdto);
			_pp.setCreateBy(users.getRealname());
			_pp.setUpdateBy(users.getRealname());
			_pp.setProjectID(projectId);
			_pp.setSchemaId(users.getSchemaId());
			ppDao.insertSelective(_pp);
		});
		return HttpStatus.OK.name();
	}

	public String updateProjectContacts(Users users, Integer projectId, List<ContactsListDto> dtos) {
		dtos.forEach(dto -> {
			dto.setProjectId(projectId);
			dto.setCurrentUserName(users.getRealname());
		});
		ProjectContactsDto pContactsDto = new ProjectContactsDto();
		pContactsDto.setProject(new ProjectVo(projectId, ""));
		pContactsDto.setContacts(dtos);
		pContactsDto.setUserName(users.getRealname());
		OperateResult<String> result = contactsServiceClient.updateProject(users.getSchemaId(), pContactsDto);
		if (result.getData() != null) {
			return result.getData();
		} else {
			throw new ApiException("修改项目联系人失败", result.getInnerException());
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProject(Users user, Integer projectId, ProjectCreateOrUpdateDto dto) {
		if (StringUtils.isEmpty(dto.getStartTimeStr())) {
			dto.setStartTimeStr(null);
		}
		Project project = ProjectEntityMapper.INSTANCE.createOrUpateDtoToProject(dto);
		project.setUpdateTime(new Date());
		project.setUpdateBy(user.getRealname());
		this.projectDao.updateByPrimaryKeySelective(project);
		// update contacts
		List<ContactsListDto> listDtos = ContactsEntityMapper.INSTANCE.contactsDtoToContactsListDto(dto.getContacts());
		ProjectContactsDto pContactsDto = new ProjectContactsDto();
		pContactsDto.setProject(new ProjectVo(projectId, ""));
		pContactsDto.setContacts(listDtos);
		pContactsDto.setUserName(user.getRealname());
		OperateResult<String> result = contactsServiceClient.updateProject(user.getSchemaId(), pContactsDto);
		if (result.success()) {
			// update 客户
			this.projectDao.deleteProjectCustomer(dto.getId());
			if (dto.getCustomer() != null) {
				CustomerProject cProject = new CustomerProject();
				cProject.setProjectID(project.getId());
				cProject.setCustomerID(dto.getCustomer().getId());
				cProject.setCreateBy(project.getCreateBy());
				cProject.setUpdateBy(project.getUpdateBy());
				cProject.setCreateTime(project.getCreateTime());
				cProject.setUpdateTime(project.getUpdateTime());
				cProject.setSchemaId(user.getSchemaId());
				projectCustomerDao.insertSelective(cProject);
			}

			// update 部门
			this.projectDao.deleteProjectOrg(user.getSchemaId(), projectId);
			dto.getDeptOwner().forEach(dept -> {
				ProjectOrg pOrg = new ProjectOrg();
				pOrg.setOrganizeID(dept.getId());
				pOrg.setProjectID(project.getId());
				pOrg.setCreateBy(project.getCreateBy());
				pOrg.setUpdateBy(project.getUpdateBy());
				pOrg.setCreateTime(new Date());
				pOrg.setUpdateTime(new Date());
				pOrg.setSchemaId(user.getSchemaId());
				pOrg.setOrganizeName(dept.getName());
				poDao.insertSelective(pOrg);
			});
			//
			this.projectDao.deleteProjectUser(user.getSchemaId(), projectId);
			dto.getManagerOwner().forEach(users -> {
				UserProject uProject = new UserProject();
				uProject.setUserID(users.getId());
				uProject.setProjectID(project.getId());
				uProject.setCreateBy(project.getCreateBy());
				uProject.setUpdateBy(project.getUpdateBy());
				uProject.setCreateTime(new Date());
				uProject.setUpdateTime(new Date());
				uProject.setSchemaId(user.getSchemaId());
				uProject.setUserName(users.getName());
				uProject.setOrganizeID(users.getDeptID());
				uProject.setOrganizeName(users.getDeptName());
				userProjectDao.insertSelective(uProject);
			});

			return HttpStatus.OK.name();
		} else {
			throw new ApiException("更新项目失败", result.getInnerException());
		}
	}

	@Autowired
	private ProjectTypeDao ptDao;

	@Autowired
	private ProjectTypeStepDao ptsDao;

	@Transactional(readOnly = true)
	public ProjectStepsDetailDto queryProjectSteps(Integer typeID, Users user) {
		ProjectType pType = ptDao.selectByPrimaryKey(typeID);
		ProjectStepsDetailDto detailDto = ProjectEntityMapper.INSTANCE.projectTypeToProjectStepDetailDto(pType);
		ProjectTypeStep pts = new ProjectTypeStep();
		pts.setSchemaId(user.getSchemaId());
		pts.setIsDelete(Short.parseShort("0"));
		pts.setProjectTypeID(typeID);
		List<ProjectTypeStep> ptss = ptsDao.select(pts);
		detailDto.setSteps(ProjectEntityMapper.INSTANCE.projectTypeStepToProjectStepDto(ptss));
		return detailDto;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> queryProjectParams(Users users) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<CommonParams> states = this.getParams("project_state");
		List<CommonParams> roles = this.getParams("project_role");
		ProjectType pType = new ProjectType();
		pType.setIsDelete(Short.parseShort("0"));
		pType.setSchemaId(users.getSchemaId());

		List<ProjectType> pTypes = this.ptDao.select(pType);

		// project state

		List<Map<String, Object>> stateList = new ArrayList<Map<String, Object>>();
		states.forEach(state -> {
			stateList.add(new HashMap<String, Object>() {
				{
					put("id", state.getKey());
					put("name", state.getVal());
				}
			});
		});

		reqMap.put("role", roles.stream().map(role -> role.getVal()).collect(Collectors.toList()));
		reqMap.put("state", stateList);
		reqMap.put("type", ProjectEntityMapper.INSTANCE.projectTypesToProjectTypeDtos(pTypes));
		return reqMap;
	}

	@Transactional(readOnly = true)
	public ProjectDetailDto queryDetail(Integer projectId, Users user) throws InterruptedException, ExecutionException {
		Project project = projectDao.selectByPrimaryKey(projectId);

		ProjectDetailDto detailDto = ProjectEntityMapper.INSTANCE.projectToProjectDetailDto(project);
		// dept owner
		ProjectOrg pOrg = new ProjectOrg();
		pOrg.setIsDelete(project.getIsDelete());
		pOrg.setSchemaId(user.getSchemaId());
		pOrg.setProjectID(project.getId());
		// contacts
		CompletableFuture<OperateResult<List<ContactsListDto>>> contactsFuture = CompletableFuture
				.supplyAsync(() -> contactsServiceClient.queryByProject(user.getSchemaId(), projectId));

		// TODO customer
		CompletableFuture<OperateResult<CustomerDetailDto>> customerFuture = CompletableFuture.supplyAsync(() -> {
			CustomerProject customerProject = new CustomerProject();
			customerProject.setIsDelete(Short.parseShort("0"));
			customerProject.setProjectID(project.getId());
			List<CustomerProject> cps = projectCustomerDao.select(customerProject);
			if (CollectionUtils.isNotEmpty(cps)) {
				return customerServiceClient.queryDetail(cps.get(0).getCustomerID(), user.getSchemaId());
			} else {

				return null;
			}
		});
		List<ProjectOrg> projectOrgs = poDao.select(pOrg);
		detailDto.setDeptOwner(ProjectEntityMapper.INSTANCE.projectOrgToDepartmentListDto(projectOrgs));
		// manager
		UserProject uProject = new UserProject();
		uProject.setIsDelete(project.getIsDelete());
		uProject.setSchemaId(user.getSchemaId());
		uProject.setProjectID(projectId);
		List<UserProject> ups = userProjectDao.select(uProject);
		detailDto.setManagerOwner(ProjectEntityMapper.INSTANCE.userProjectToUserListDto(ups));

		// payment
		ProjectPayment pp = new ProjectPayment();
		pp.setIsDelete(project.getIsDelete());
		pp.setProjectID(projectId);
		List<ProjectPayment> pps = this.ppDao.select(pp);
		detailDto.setPayment(ProjectEntityMapper.INSTANCE.projectPaymentToProjectPaymentDto(pps));
		// steps
		List<ProjectTypeStep> ptssList = this.projectDao.selectProjectSteps(user.getSchemaId(), projectId);
		detailDto.setSteps(ProjectEntityMapper.INSTANCE.projectTypeStepToProjectStepDto(ptssList));

		// type
		ProjectType type = ptDao.selectByPrimaryKey(project.getProjectTypeID());
		if (type != null) {
			detailDto.setTypeName(type.getName());
		}

		detailDto.setContacts(contactsFuture.get().getData());
		OperateResult<CustomerDetailDto> cusotmeResult = null;
		if ((cusotmeResult = customerFuture.get()) != null) {
			detailDto.setCustomer(new CustomerVo(cusotmeResult.getData().getId(), cusotmeResult.getData().getName()));
		}
		return detailDto;
	}

	/**
	 * ✅ FIXME YSTCRM-272 项目-列表，排序按创建时间倒序
	 * 
	 * @param user
	 * @param customerId
	 * @param keyword
	 * @param name
	 * @param state
	 * @param typeId
	 * @param curPage
	 * @param perPageSum
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryProjects(Users user, Integer customerId, String keyword, String name, String state,
			String typeId, Integer curPage, Integer perPageSum) throws InterruptedException, ExecutionException {
		List<ProjectListDto> projectListDtos = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);

		params.put("customerId", customerId);
		params.put("keyword", keyword);
		params.put("name", name);
		params.put("state", state);
		params.put("typeId", typeId);
		params.put("schemaId", user.getSchemaId());
		params.put("page", page);
		Integer totalRecords = this.projectDao.selectCountBy(params);

		if (totalRecords > 0) {
			projectListDtos = this.projectDao.selectProjectBy(params);
			int[] projectIds = projectListDtos.stream().mapToInt(dto -> Integer.valueOf(dto.getId())).toArray();
			int[] customerIds = projectListDtos.stream().filter(dto -> dto.getCustomer() != null)
					.mapToInt(dto -> dto.getCustomer().getId()).toArray();
			List<Project> projects = this.projectDao.selectProjectConfigStepsAndHistorySetps(user.getSchemaId(),
					projectIds);

			CompletableFuture<OperateResult<List<CustomerListDto>>> customerFuture = CompletableFuture
					.supplyAsync(() -> this.customerServiceClient.queryByIds(user.getSchemaId(), customerIds));
			this.configCurrentStepForProject(projects, projectListDtos);
			// customer
			OperateResult<List<CustomerListDto>> result = customerFuture.get();
			if (result.success()) {
				projectListDtos.forEach(dto -> {
					if (dto.getCustomer() != null) {
						Optional<CustomerListDto> _result = result.getData().stream()
								.filter(customer -> customer.getId().equals(Integer.valueOf(dto.getCustomer().getId())))
								.findFirst();
						if (_result.isPresent()) {
							dto.setCustomer(new CustomerVo(dto.getCustomer().getId(), _result.get().getName()));
						}
					}
				});
			}
		}
		page.setTotalRecords(totalRecords);
		return OperateResult.renderPage(page, projectListDtos);
	}

	private void configCurrentStepForProject(List<Project> infos, List<ProjectListDto> listDtos) {
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Project project : infos) {
			Integer isLastStep = 0;
			Integer isOverTime = 0;

			long stepFromNow = 0;

			Long commitTime = null;
			long warningDay = 0;
			CurrentStep _currentStep = new CurrentStep();

			if (CollectionUtils.isEmpty(project.getHistorySteps())) {
				isLastStep = 0;
				isOverTime = 0;
			} else {
				if (project.getHistorySteps().size() == project.getTypeSteps().size()) {
					// 最后阶段
					isLastStep = 1;
				}
				project.getHistorySteps().sort((a, b) -> b.compareTo(a));
				ProjectStep currentStep = project.getHistorySteps().get(project.getHistorySteps().size() - 1);
				_currentStep.setId(String.valueOf(currentStep.getId()));
				_currentStep.setTime(currentStep.getTime());
				_currentStep.setTimeStr(dFormat.format(currentStep.getTime()));

				commitTime = currentStep.getTime().getTime();
				// 阶段进行时间
				long day = 0, diff = 0;
				diff = new Date().getTime() - commitTime;
				day = diff / (24 * 60 * 60 * 1000);
				stepFromNow = day;
				if (CollectionUtils.isNotEmpty(project.getTypeSteps())) {
					Optional<ProjectTypeStep> result = project.getTypeSteps().stream()
							.filter(typeStep -> typeStep.getId().equals(currentStep.getProjectTypeStepID()))
							.findFirst();
					if (result.isPresent()) {
						_currentStep.setMemo(result.get().getMemo());
						warningDay = result.get().getDay();
						if (stepFromNow > warningDay) {
							isOverTime = 1;
						} else {
							isOverTime = 0;
						}
					}
				}
			}
			for (int i = 0; i < listDtos.size(); i++) {
				if (listDtos.get(i).getId().equals(project.getId().toString())) {
					_currentStep.setIsLastStep(String.valueOf(isLastStep));
					_currentStep.setIsOverTime(String.valueOf(isOverTime));
					_currentStep.setStepDay(String.valueOf(stepFromNow));
					listDtos.get(i).setCurrentStep(_currentStep);
					break;
				}
			}
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProjectSteps(Integer projectId, Users users, List<ProjectStepsDto> dtos) {
		// clear all
		this.projectDao.deleteProjectSteps(projectId, users.getSchemaId());
		List<ProjectStep> projectSteps = ProjectEntityMapper.INSTANCE.projectStepsDtoToProjectStep(dtos);
		projectSteps.forEach(ps -> {
			ps.setCreateBy(users.getRealname());
			ps.setUpdateBy(users.getRealname());
			ps.setSchemaId(users.getSchemaId());
			ps.setProjectID(projectId);
			this.projectStepDao.insertSelective(ps);
		});
		return HttpStatus.OK.name();
	}

	public static void main(String[] args) throws ParseException {
		List<ProjectStep> list = new ArrayList<ProjectStep>();
		ProjectStep pStep1 = new ProjectStep();
		pStep1.setId(1);
		ProjectStep pStep2 = new ProjectStep();
		pStep2.setId(2);

		ProjectStep pStep3 = new ProjectStep();
		pStep3.setId(5);
		list.add(pStep3);
		list.add(pStep1);
		list.add(pStep2);

		// for (int i = 0; i < list.size(); i++) {
		// System.err.println(list.get(i).getId());
		// }
		//
		// list.sort((a,b)-> b.compareTo(a));
		// for (int i = 0; i < list.size(); i++) {
		// System.err.println(list.get(i).getId());
		// }
		//

		String time1 = "2017-03-01";
		String time2 = "2017-03-01";

		DateFormat dfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Long diff = dfDateFormat.parse(time2).getTime() - dfDateFormat.parse(time1).getTime();

		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;

		// 已经逾期了 如果是未完成 需要显示 延期，完成了就不显示好了

		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		System.out.println(day + "天  " + hour + "小时" + min);
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProjectSteps(Users user, Integer projectId, String state) {

		Project project = new Project();
		project.setId(projectId);
		project.setUpdateBy(user.getRealname());
		project.setUpdateTime(new Date());
		project.setSchemaId(user.getSchemaId());
		project.setState(Byte.parseByte(state));
		Integer result = this.projectDao.updateByPrimaryKeySelective(project);
		if (result > 0) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("更新项目状态失败");
		}
	}
}
