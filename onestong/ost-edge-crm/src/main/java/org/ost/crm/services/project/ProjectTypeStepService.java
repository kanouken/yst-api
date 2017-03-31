package org.ost.crm.services.project;

import java.util.Date;

import org.apache.commons.collections.MapUtils;
import org.common.tools.exception.ApiException;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.crm.dao.project.ProjectTypeStepDao;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
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
	public ProjectTypeDao projectTypeDao;

	@Autowired
	public ProjectTypeStepDao projectTypeStepDao;

	/**
	 * 添加和编辑项目类型阶段
	 * @param projectTypeID
	 * @param users
	 * @param projectStepsDetailDto
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createOrUpdateTypeStep(Integer projectTypeID, Users users, ProjectStepsDetailDto projectStepsDetailDto) {
		
		//获取projectType中预警信息
		ProjectType projectType = new ProjectType();
		projectType.setId(projectTypeID);
		projectType.setCycWarningDay(MapUtils.getInteger(projectStepsDetailDto.getCycWarning(), "day"));
		projectType.setCycWarningEnable(MapUtils.getString(projectStepsDetailDto.getCycWarning(), "isEnable"));
		projectType.setStartTimeWarningDay(MapUtils.getInteger(projectStepsDetailDto.getStartTimeWarning(), "day"));
		projectType.setStartTimeWarningEnable(MapUtils.getString(projectStepsDetailDto.getStartTimeWarning(), "isEnable"));
		
		//对projectType进行update
		projectTypeDao.updateParam(projectType);

		//根据projectTypeID删除ProjectTypeStep中相关信息
		ProjectTypeStep projectTypeStep = new ProjectTypeStep();
		projectTypeStepDao.deleteProjectTypeStep(projectTypeID);
		
		//添加projectTypeStep中信息
		projectTypeStep.setProjectTypeID(projectTypeID);
		projectTypeStep.setCreateBy(users.getRealname());
		projectTypeStep.setSchemaId(users.getSchemaId());
		projectTypeStep.setUpdateBy(projectTypeStep.getCreateBy());
		projectTypeStep.setCreateTime(new Date());
		projectTypeStep.setUpdateTime(new Date());
		projectTypeStep.setIsDelete(Short.valueOf("0"));
		
		//循环取出steps中信息添加到projectTypeStep
		projectStepsDetailDto.getSteps().forEach(s -> {
			projectTypeStep.setMemo(s.getMemo());
			projectTypeStep.setRate(Integer.parseInt(s.getRate()));
			projectTypeStep.setStep(Double.parseDouble(s.getStep()));
			projectTypeStep.setDay(MapUtils.getInteger(s.getWarning(), "day"));
			projectTypeStep.setIsEnable(MapUtils.getByte(s.getWarning(), "isEnable"));
		});
		
		projectTypeStepDao.insert(projectTypeStep);
	}
}
