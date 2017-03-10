package org.ost.crm.dao.project;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.ProjectTypeStep;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectTypeStepDao extends Mapper<ProjectTypeStep> {
	Integer deleteProjectTypeStep(@Param("projectTypeID") Integer projectTypeID);
}