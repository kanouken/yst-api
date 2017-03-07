package org.ost.crm.dao.project;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.Project;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectDao extends Mapper<Project> {

	Integer deleteProjectSteps(@Param("projectId") Integer projectId, @Param("schemaId") String schemaId);

}
