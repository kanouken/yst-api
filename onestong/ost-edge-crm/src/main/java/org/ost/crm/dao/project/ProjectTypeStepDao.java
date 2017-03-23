package org.ost.crm.dao.project;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.ProjectTypeStep;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectTypeStepDao extends Mapper<ProjectTypeStep> {
	
	//根据projectTypeID删除ProjectTypeStep表中相关信息
	Integer deleteProjectTypeStep(@Param("projectTypeID") Integer projectTypeID);
	

}