package org.ost.crm.services.project;

import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.CustomerServiceClient;
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
import org.ost.entity.customer.dto.CustomerProjectDto;
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
		projectDao.insert(project);
		ProjectContactsDto projectContactsDto = new ProjectContactsDto();
		projectContactsDto.setProject(new ProjectVo(project.getId(), project.getName()));
		projectContactsDto.setContacts(ContactsEntityMapper.INSTANCE.contactsDtoToContactsListDto(dto.getContacts()));
		projectContactsDto.setUserName(user.getRealname());
		OperateResult<String> result = this.contactsServiceClient.updateContactProject(user.getSchemaId(),
				projectContactsDto);
		if (result.success()) {
			// project customer
			CustomerProjectDto customerProjectDto = new CustomerProjectDto();
			customerProjectDto.setCustomer(dto.getCustomer());
			customerProjectDto.setProject(new ProjectVo(project.getId(), project.getName()));
			customerProjectDto.setUserName(user.getRealname());
			OperateResult<String> result2 = this.customerServiceClient.createCustomerProject(user.getSchemaId(),
					customerProjectDto);
			if (result2.success()) {
				dto.getDeptOwner().forEach(dept -> {
					ProjectOrg pOrg = new ProjectOrg();
					pOrg.setOrganizeID(dept.getId());
					pOrg.setProjectID(project.getId());
					pOrg.setCreateBy(project.getCreateBy());
					pOrg.setUpdateBy(project.getUpdateBy());
					pOrg.setCreateTime(new Date());
					pOrg.setUpdateTime(new Date());
					pOrg.setSchemaId(user.getSchemaId());
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
					userProjectDao.insertSelective(uProject);
				});
			} else {
				throw new ApiException("新增项目失败", result2.getInnerException());
			}

		} else {
			throw new ApiException("新增项目失败", result.getInnerException());
		}
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
	public String updateProject(Users user, ProjectCreateOrUpdateDto dto) {
		Project project = ProjectEntityMapper.INSTANCE.createOrUpateDtoToProject(dto);
		project.setUpdateTime(new Date());
		project.setUpdateBy(user.getRealname());
		project.setSchemaId(user.getSchemaId());
		this.projectDao.updateByPrimaryKeySelective(project);
		// update contacts
		List<ContactsListDto> listDtos = ContactsEntityMapper.INSTANCE.contactsDtoToContactsListDto(dto.getContacts());
		ProjectContactsDto pContactsDto = new ProjectContactsDto();
		pContactsDto.setProject(new ProjectVo(Integer.parseInt(dto.getId()), ""));
		pContactsDto.setContacts(listDtos);
		pContactsDto.setUserName(user.getRealname());
		OperateResult<String> result = contactsServiceClient.updateProject(user.getSchemaId(), pContactsDto);
		if (result.getData() != null) {
			// update TODO 修改客户

			// TODO 部门

			// TODO 经理

			return result.getData();
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
					put("id", state.getId());
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
	public Project queryDetail(Integer projectId, Users user) {
		
		
		return null;
	}

	@Transactional(readOnly = true)
	public Project queryProjects(Users user, Integer customerId, String keyword, String name, String state,
			String typeId, Integer curPage, Integer perPageSum) {
		// TODO Auto-generated method stub
		return null;
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

}
