package org.ost.crm.dao.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.ost.entity.project.ProjectType;
import org.springframework.stereotype.Repository;

import feign.Param;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProjectTypeDao extends Mapper<ProjectType> {
	Integer selectTypeCount(@Param("parmamsMap") Map<String, String> params);

	List<ProjectType> selectTypeList(@Param("parmamsMap") Map<String, String> params, RowBounds rb);
	
}