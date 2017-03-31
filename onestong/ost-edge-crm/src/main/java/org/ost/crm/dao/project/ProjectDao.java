package org.ost.crm.dao.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectListDto;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectDao extends Mapper<Project> {

	Integer deleteProjectSteps(@Param("projectId") Integer projectId, @Param("schemaId") String schemaId);

	List<ProjectTypeStep> selectProjectSteps(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

	@Delete("delete from tbl_organize_project where schemaID=#{schemaId} and projectID = #{projectId}")
	void deleteProjectOrg(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

	@Delete("delete from tbl_user_project where schemaID= #{schemaId} and projectID= #{projectId}")
	void deleteProjectUser(@Param("schemaId") String schemaId, @Param("projectId") Integer projectId);

	Integer selectCountBy(Map<String, Object> params);
	Integer selectCountByVisit(Map<String, Object> params);

	List<ProjectListDto> selectProjectBy(Map<String, Object> params);

	
	List<ProjectListDto> selectProjectByVisit(Map<String, Object> params);

	
	List<Project> selectProjectConfigStepsAndHistorySetps(@Param("schemaId") String schemaId,
			@Param("projectIds") int[] projectIds);
	
	@Delete("delete from tbl_project_customer where projectID = #{id}")
	void deleteProjectCustomer(@Param("id")String id);

}
