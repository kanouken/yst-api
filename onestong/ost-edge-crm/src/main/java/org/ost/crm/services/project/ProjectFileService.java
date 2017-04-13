package org.ost.crm.services.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.dao.project.ProjectFileDao;
import org.ost.entity.project.ProjectFile;
import org.ost.entity.project.dto.ProjectFileDto;
import org.ost.entity.project.mapper.ProjectEntityMapper;
import org.ost.entity.project.vo.ProjectTypeVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectFileService {
	@Autowired
	private ProjectFileDao pfDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String saveProjectFile(Users user, String fid, String name, Integer projectId) {
		ProjectFile pFile = new ProjectFile();
		pFile.setCreateBy(user.getRealname());
		pFile.setUpdateBy(user.getRealname());
		pFile.setCreateTime(new Date());
		pFile.setUpdateTime(new Date());
		pFile.setFid(fid);
		pFile.setProjectID(projectId);
		pFile.setName(name);
		pFile.setSchemaId(user.getSchemaId());
		pfDao.insertSelective(pFile);
		return HttpStatus.OK.name();
	}

	@Transactional(readOnly = true)
	public Map<String, Object> queryProjectFiles(Users user, Page page, Integer projectId) {
		ProjectFile pFile = new ProjectFile();
		pFile.setIsDelete(Short.parseShort("0"));
		pFile.setProjectID(projectId);
		List<ProjectFileDto> records = new ArrayList<ProjectFileDto>();
		Integer totalRecords = pfDao.selectCount(pFile);
		if (totalRecords > 0) {
			List<ProjectFile> pFiles = this.pfDao.select(pFile);
			records = ProjectEntityMapper.INSTANCE.projectFileToProjectFileDto(pFiles);
		}
		page.setTotalRecords(totalRecords);

		return OperateResult.renderPage(page, records);
	}

	/**
	 * 批量逻辑删除
	 * 
	 * @param user
	 * @param fids
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteFile(Users user,Integer projectId, String fids) {
		List<String> list = getList(fids);
		int result = this.pfDao.deleteProjectFile(projectId,list);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("删除失败");
	}

	public List<String> getList(String fids) {
		List<String> list = new ArrayList<String>();
		String[] str = fids.split(",");
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		return list;
	}

	/**
	 * 回收站列表
	 * 
	 * @param user
	 * @param curPage
	 * @param perPageSum
	 * @param name
	 * @param updateTime
	 * @param updateBy
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryDeleteProjectFiles(Users user, Integer curPage, Integer perPageSum, String name,
			String updateTime, String updateBy) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds row = new RowBounds(page.getNextPage(), page.getPerPageSum());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("updateTime", updateTime);
		params.put("updateBy", updateBy);

		Integer totalRecords = pfDao.selectProjectFileCount(params);
		List<ProjectFile> projectFileList = new ArrayList<ProjectFile>();
		if (totalRecords > 0) {
			projectFileList = pfDao.selectProjectFileList(params, row);
		}
		return OperateResult.renderPage(page, projectFileList);
	}

	/**
	 * 彻底删除
	 * @param user
	 * @param id
	 * @return
	 */
	public String deleteProjectFiles(Users user, Integer id) {
		ProjectFile projectFile = new ProjectFile();
		projectFile.setSchemaId(user.getSchemaId());
		projectFile.setId(id);
		projectFile.setIsDelete(Short.valueOf("1"));
		int result = this.pfDao.delete(projectFile);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("彻底删除失败");
	}
	
	/**
	 * 还原
	 * @param user
	 * @param id
	 * @return
	 */
	public String updateProjectFiles(Users user, Integer id){
		ProjectFile projectFile = new ProjectFile();
		projectFile.setId(id);
		projectFile.setIsDelete(Short.valueOf("0"));
		projectFile.setSchemaId(user.getSchemaId());
		int result = this.pfDao.updateByPrimaryKeySelective(projectFile);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("还原失败");
	}
	
	/**
	 * 全部还原
	 * @param user
	 * @param fids
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateAllProjectFiles(Users user,String fids) {
		List<String> list = getList(fids);
		int result = this.pfDao.updateAllProjectFiles(list);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("全部还原失败");
	}
	
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteAllProjectFiles(Users user,String fids) {
		List<String> list = getList(fids);
		int result = this.pfDao.deleteAllProjectFiles(list);
		if (result > 0) {
			return HttpStatus.OK.name();
		}
		throw new ApiException("全部清空失败");
	}
	

}
