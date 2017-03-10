package org.ost.crm.services.project;

import java.util.Date;

import org.apache.commons.collections.MapUtils;
import org.ost.crm.dao.project.ProjectTypeDao;
import org.ost.crm.dao.project.ProjectTypeStepDao;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * 添加项目类型阶段
	 * 
	 * @param projectTypeID
	 * @param users
	 * @param pdto
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createOrUpdateTypeStep(Integer projectTypeID, Users users, ProjectStepsDetailDto pdto) {
		ProjectType pro = new ProjectType();
		pro.setCycWarningDay(MapUtils.getInteger(pdto.getCycWarning(), "cycWarningDay"));
		pro.setCycWarningEnable(MapUtils.getString(pdto.getCycWarning(), "cycWarningEnable"));
		pro.setStartTimeWarningDay(MapUtils.getInteger(pdto.getStartTimeWarning(), "startTimeWarningDay"));
		pro.setStartTimeWarningEnable(MapUtils.getString(pdto.getStartTimeWarning(), "startTimeWarningEnable"));
		pro.setId(projectTypeID);
		projectTypeDao.updateParam(pro);

		ProjectTypeStep ps = new ProjectTypeStep();
		projectTypeStepDao.deleteProjectTypeStep(projectTypeID);

		ps.setProjectTypeID(projectTypeID);
		ps.setCreateBy(users.getRealname());
		ps.setSchemaId(users.getSchemaId());
		ps.setUpdateBy(ps.getCreateBy());
		ps.setCreateTime(new Date());
		ps.setUpdateTime(new Date());
		ps.setIsDelete(Short.valueOf("0"));

		pdto.getSteps().forEach(s -> {
			ps.setMemo(s.getMemo());
			ps.setRate(Integer.parseInt(s.getRate()));
			ps.setStep(Double.parseDouble(s.getStep()));
			ps.setDay(MapUtils.getInteger(s.getWarning(), "day"));
			ps.setIsEnable(MapUtils.getByte(s.getWarning(), "isEnable"));
		});
		projectTypeStepDao.insert(ps);
	}

}
