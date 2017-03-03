package org.ost.crm.services.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.vo.ProjectTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ProjectTypeService {

	@Autowired
	public ProjectTypeDao projectTypeDao;

	/**
	 * 编辑项目分类
	 * @param projectType
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateProjectType(ProjectType projectType) {
		projectType.setId(projectType.getId());
		this.projectTypeDao.updateByPrimaryKey(projectType);
	}

	/**
	 * 新增项目分类
	 * @param PTVo
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void CreatemProjectType(ProjectTypeVo proTVo) throws JsonProcessingException {
		ProjectType pType = new ProjectType();
		pType.setName(proTVo.getName());
		pType.setCycWarningDay(proTVo.getCycWarningDay());
		pType.setCycWarningEnable(proTVo.getCycWarningEnable());
		pType.setStartTimeWarningDay(proTVo.getStartTimeWarningDay());
		pType.setStartTimeWarningEnable(proTVo.getStartTimeWarningEnable());
		pType.setIsDelete(Short.valueOf("0"));
		pType.setSchemaId(proTVo.getSchemaId());
		pType.setCreateTime(new Date());
		pType.setUpdateTime(new Date());
		pType.setCreateBy(proTVo.getCreateBy());
		pType.setUpdateBy(proTVo.getUpdateBy());
		this.projectTypeDao.insert(pType);
		
	}

	/**
	 * 项目分类列表
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageEntity<ProjectType> proTypeList(Integer curPage, Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds row = new RowBounds(page.getNextPage(), page.getPerPageSum());
		Map<String, String> params = null;
		Integer totalRecord = this.projectTypeDao.selectTypeCount(params);
		List<ProjectType> proList = this.projectTypeDao.selectTypeList(params, row);
		if (totalRecord > 0) {
			proList = this.projectTypeDao.selectTypeList(params, row);
		}
		PageEntity<ProjectType> p = new PageEntity<ProjectType>();
		p.setCurPage(curPage);
		p.setTotalRecord(perPageSum);
		p.setObjects(proList);
		return p;
	}
	
	/**
	 * 获取项目分类详情
	 * @param id
	 */
	@Transactional(readOnly=true)
	public void proTypeDetail(Integer id){
		ProjectType pro=new ProjectType();
		pro.setId(id);
		this.projectTypeDao.selectOne(pro);
	}
	
	/**
	 * 删除项目分类
	 * @param id
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deleteType(Integer id){
		projectTypeDao.deleteByPrimaryKey(id);
	}

}
