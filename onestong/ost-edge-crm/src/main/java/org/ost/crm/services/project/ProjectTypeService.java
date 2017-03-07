package org.ost.crm.services.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 * 
	 * @param PTVo
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createmProjectType(Users user, ProjectTypeVo proTVo) throws JsonProcessingException {
		ProjectType pType = new ProjectType();
		pType.setName(proTVo.getName());
		pType.setCycWarningDay(proTVo.getCycWarningDay());
		pType.setCycWarningEnable(proTVo.getCycWarningEnable());
		pType.setStartTimeWarningDay(proTVo.getStartTimeWarningDay());
		pType.setStartTimeWarningEnable(proTVo.getStartTimeWarningEnable());
		pType.setIsDelete(Short.valueOf("0"));
		pType.setSchemaId(user.getSchemaId());
		pType.setCreateTime(new Date());
		pType.setUpdateTime(new Date());
		pType.setCreateBy(user.getRealname());
		pType.setUpdateBy(pType.getCreateBy());
		this.projectTypeDao.insert(pType);
	}

	/**
	 * 编辑项目分类
	 * 
	 * @param projectType
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateProjectType(Integer id, Users user, ProjectTypeVo proTVo) {
		ProjectType projectType = new ProjectType();
		projectType.setId(id);
		projectType.setName(proTVo.getName());
		projectType.setCycWarningDay(proTVo.getCycWarningDay());
		projectType.setCycWarningEnable(proTVo.getCycWarningEnable());
		projectType.setStartTimeWarningDay(proTVo.getStartTimeWarningDay());
		projectType.setStartTimeWarningEnable(proTVo.getStartTimeWarningEnable());
		projectType.setUpdateTime(new Date());
		projectType.setUpdateBy(projectType.getCreateBy());
		int result = this.projectTypeDao.updateByPrimaryKeySelective(projectType);
		if (result > 0) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("编辑项目分类失败");
		}
	}

	/**
	 * 项目分类列表
	 * 
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> proTypeList(Integer id, String name, Users user, Integer curPage, Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds row = new RowBounds(page.getNextPage(), page.getPerPageSum());
		Map<String, String> params = null;
		Integer totalRecord = this.projectTypeDao.selectTypeCount(params);
		List<ProjectTypeVo> proList = this.projectTypeDao.selectTypeList(params, row);
		if (totalRecord > 0) {
			proList = this.projectTypeDao.selectTypeList(params, row);
		}
		PageEntity<ProjectTypeVo> p = new PageEntity<ProjectTypeVo>();
		p.setCurPage(curPage);
		p.setTotalRecord(perPageSum);
		return OperateResult.renderPage(page, proList);
	}

	/**
	 * 获取项目分类详情
	 * 
	 * @param id
	 */
	@Transactional(readOnly = true)
	public ProjectTypeVo proTypeDetail(Integer id, Users user) {
		ProjectType pro = new ProjectType();
		pro.setIsDelete(Short.valueOf("0"));
		pro.setId(id);
		return this.projectTypeDao.selectTypeById(pro);
		//return this.projectTypeDao.selectOne(pro);
	}

	/**
	 * 删除项目分类
	 * 
	 * @param id
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteType(Integer id, Users user) {
		ProjectType pro = new ProjectType();
		pro.setIsDelete(Short.valueOf("1"));
		pro.setId(id);
		pro.setSchemaId(user.getSchemaId());
		int result = projectTypeDao.updateByPrimaryKeySelective(pro);
		if (result > 0) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("删除项目分类失败");
		}
	}
}
