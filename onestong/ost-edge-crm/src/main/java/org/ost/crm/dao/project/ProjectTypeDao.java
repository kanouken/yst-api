package org.ost.crm.dao.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.vo.ProjectTypeVo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ProjectTypeDao extends Mapper<ProjectType> {
	//获取总记录条数
	Integer selectProjectTypeCount(@Param("params") Map<String,Object> params);
	//获取ProjectType集合
	List<ProjectTypeVo> selectProjectTypeVoList(@Param("params") Map<String,Object> params,RowBounds rb);
	//根据id查找详情
	ProjectTypeVo selectTypeById(@Param("projectType") ProjectType projectType);
	//修改projectType中预警信息
	Integer updateParam(@Param("projectType")ProjectType projectType);
}