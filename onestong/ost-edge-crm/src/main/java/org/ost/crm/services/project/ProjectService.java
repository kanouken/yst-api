package org.ost.crm.services.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.dao.project.ProjectDao;
import org.ost.crm.dao.project.ProjectOrgDao;
import org.ost.crm.dao.project.ProjectPaymentDao;
import org.ost.crm.dao.project.ProjectPaymentExample;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.crm.dao.project.ProjectTypeStepDao;
import org.ost.crm.dao.project.UserProjectDao;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectFile;
import org.ost.entity.project.ProjectOrg;
import org.ost.entity.project.ProjectPayment;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.UserProject;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.project.mapper.ProjectEntityMapper;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hp.hpl.sparta.xpath.TrueExpr;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ProjectFile projectFileDao;

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
		projectDao.insert(project);
		OperateResult<String> result = this.contactsServiceClient.updateContactProject(user.getSchemaId(), dto);
		if (result.getData() != null) {
			//
			OperateResult<String> result2 = this.customerServiceClient.createCustomerProject(user.getSchemaId(),
					dto.getCustomer().getId(), project.getId(), user);

			if (result2.getData() != null) {
				//
				dto.getDeptOwners().forEach(dept -> {
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
				dto.getManagerOwners().forEach(users -> {
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
		return "ok";
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
			ppDao.insertSelective(_pp);
		});
		return "ok";
	}

	public String updateProjectContacts(Users users, Integer projectId, List<ContactsListDto> dtos) {
		dtos.forEach(dto -> {
			dto.setProjectId(projectId);
			dto.setCurrentUserName(users.getRealname());
		});
		OperateResult<String> result = contactsServiceClient.updateProject(users.getSchemaId(), dtos);
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
		OperateResult<String> result = contactsServiceClient.updateProject(user.getSchemaId(), listDtos);
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

}
