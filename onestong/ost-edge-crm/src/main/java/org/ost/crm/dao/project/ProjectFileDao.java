package org.ost.crm.dao.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.project.ProjectFile;

import tk.mybatis.mapper.common.Mapper;

public interface ProjectFileDao extends Mapper<ProjectFile> {

	// 批量逻辑删除
	public int deleteProjectFile(@Param("fids") List<String> fids);

	// public int deleteProjectFile(@Param("fids")String fid);

	// 获取总记录条数
	Integer selectProjectFileCount(@Param("params") Map<String, Object> params);

	// 获取集合
	List<ProjectFile> selectProjectFileList(@Param("params") Map<String, Object> params, RowBounds row);

	// 全部还原
	public int restoreAllProjectFiles(@Param("fids") List<String> fids);

	// 全部还原
	public int deleteAllProjectFiles(@Param("fids") List<String> fids);
}