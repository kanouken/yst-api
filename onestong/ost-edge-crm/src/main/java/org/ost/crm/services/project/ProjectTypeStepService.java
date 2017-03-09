package org.ost.crm.services.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.crm.dao.project.ProjectTypeStepDao;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectTypeStepDto;
import org.ost.entity.project.vo.ProjectTypeVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectTypeStepService {

	@Autowired 
	ProjectTypeDao projectTypeDao;
	
	@Autowired
	public ProjectTypeStepDao projectTypeStepDao;

	/**
	 * 添加项目类型阶段
	 * 
	 * @param projectTypeID
	 * @param users
	 * @param psdtos
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void insertTypeStep(Integer projectTypeID, Users users,ProjectTypeStepDto pTDto) {
		Map<String, Object> typeStep=new HashMap<>();
		typeStep.put("cycWarningDay","cycWarningDay");
		typeStep.put("cycWarningEnable","cycWarningEnable");
		typeStep.put("startTimeWarningDay", "startTimeWarningDay");
		typeStep.put("startTimeWarningEnable","startTimeWarningEnable");
		ProjectType pt=projectTypeDao.updateParam(typeStep);
		if(pt!=null){
			projectTypeDao.insert(pt);
		}
		ProjectTypeStep pts=new ProjectTypeStep();
		//projectTypeStepDao.delete(pts);
		pts.setCreateBy(users.getRealname());
		pts.setUpdateBy(pts.getCreateBy());
		pts.setCreateTime(new Date());
		pts.setUpdateTime(new Date());
		pts.setIsDelete(Short.valueOf("0"));
		pts.setSchemaId(users.getSchemaId());
		pts.setProjectTypeID(projectTypeID);
		pts.setMemo(pTDto.getMemo());
		pts.setDay(pTDto.getDay());
		pts.setStep(pTDto.getStep());
		pts.setRate(pTDto.getRate());
		pts.setIsEnable(Byte.valueOf("1"));
		projectTypeStepDao.insert(pts);
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateTypeStep(Integer projectTypeID, Users users,ProjectTypeStepDto pTDto) {
		ProjectTypeStep pts=new ProjectTypeStep();
		pts.setId(projectTypeID);
		pts.setUpdateBy(pts.getCreateBy());
		pts.setUpdateTime(new Date());
		pts.setMemo(pTDto.getMemo());
		pts.setStep(pTDto.getStep());
		pts.setDay(pTDto.getDay());
		projectTypeStepDao.updateByPrimaryKeySelective(pts);
		return HttpStatus.OK.name();
	}

}
