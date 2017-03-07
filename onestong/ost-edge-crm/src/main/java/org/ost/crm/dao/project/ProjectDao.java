package org.ost.crm.dao.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectTypeStep;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectDao extends Mapper<Project> {

	Integer deleteProjectSteps(@Param("projectId") Integer projectId, @Param("schemaId") String schemaId);

	List<ProjectTypeStep> selectProjectSteps(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

	@Delete("delete from tbl_organize_project where schemaID=#{projectId} and projectID = #{projectId}")
	void deleteProjectOrg(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

	@Delete("delete from tbl_user_project where schemaID= #{schemaId} and projectID= #{projectId}")
	void deleteProjectUser(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

}
