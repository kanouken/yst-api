package org.ost.crm.services.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.dao.project.ProjectFileDao;
import org.ost.entity.project.ProjectFile;
import org.ost.entity.project.dto.ProjectFileDto;
import org.ost.entity.project.mapper.ProjectEntityMapper;
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

}
