package org.ost.crm.services.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.vo.ProjectTypeVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ProjectTypeService {

	@Autowired
	public ProjectTypeDao projectTypeDao;

	/**
	 * 新增项目分类
	 * @param user
	 * @param projectTypeVo
	 * @throws JsonProcessingException
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createProjectType(Users user, ProjectTypeVo projectTypeVo) throws JsonProcessingException {
		ProjectType projectType = new ProjectType();
		projectType.setName(projectTypeVo.getName());
		projectType.setCycWarningDay(projectTypeVo.getCycWarningDay());
		projectType.setCycWarningEnable(projectTypeVo.getCycWarningEnable());
		projectType.setStartTimeWarningDay(projectTypeVo.getStartTimeWarningDay());
		projectType.setStartTimeWarningEnable(projectTypeVo.getStartTimeWarningEnable());
		projectType.setIsDelete(Short.valueOf("0"));
		projectType.setSchemaId(user.getSchemaId());
		projectType.setCreateTime(new Date());
		projectType.setUpdateTime(new Date());
		projectType.setCreateBy(user.getRealname());
		projectType.setUpdateBy(projectType.getCreateBy());
		int result =this.projectTypeDao.insert(projectType);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("新增项目分类失败");
	}

	/**
	 * 编辑项目分类
	 * @param id
	 * @param user
	 * @param projectTypeVo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProjectType(Integer id, Users user, ProjectTypeVo projectTypeVo) {
		ProjectType projectType = new ProjectType();
		projectType.setId(id);
		projectType.setName(projectTypeVo.getName());
		projectType.setCycWarningDay(projectTypeVo.getCycWarningDay());
		projectType.setCycWarningEnable(projectTypeVo.getCycWarningEnable());
		projectType.setStartTimeWarningDay(projectTypeVo.getStartTimeWarningDay());
		projectType.setStartTimeWarningEnable(projectTypeVo.getStartTimeWarningEnable());
		projectType.setUpdateTime(new Date());
		projectType.setUpdateBy(projectType.getCreateBy());
		
		//update
		int result = this.projectTypeDao.updateByPrimaryKeySelective(projectType);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("编辑项目分类失败");
	}

	/**
	 * 项目分类列表
	 * 
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryMember(String names, Users users, Integer curPage, Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds row = new RowBounds(page.getNextPage(), page.getPerPageSum());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schemaID", users.getSchemaId());
		params.put("names",names);
		
		Integer totalRecord = this.projectTypeDao.selectProjectTypeCount(params);
		List<ProjectTypeVo> projectTypeVoList = new ArrayList<ProjectTypeVo>();
		if (totalRecord > 0) {
			projectTypeVoList = this.projectTypeDao.selectProjectTypeVoList(params, row);
		}

		return OperateResult.renderPage(page, projectTypeVoList);
	}

	/**
	 * 获取项目分类详情
	 * @param id
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public ProjectTypeVo detailProjectType(Integer id, Users user) {
		
		ProjectType projectType = new ProjectType();
		projectType.setId(id);
		
		return this.projectTypeDao.selectTypeById(projectType);
	}

	/**
	 * 删除项目分类
	 * @param id
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteProjectType(Integer id, Users user) {
		
		ProjectType projectType = new ProjectType();
		projectType.setId(id);
		projectType.setSchemaId(user.getSchemaId());
		projectType.setIsDelete(Short.valueOf("1"));
		
		//delete
		int result = projectTypeDao.updateByPrimaryKeySelective(projectType);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
			throw new ApiException("删除项目分类失败");
	}
}
