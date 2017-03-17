package com.oz.onestong.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AppVersionMapper {
	Map<String, Object>  selectVersionByDevice(@Param("device") String device );
	
	
	Integer  countAll();
	
	List<Map<String, Object>> selectAllVersions();
	
	void updateVersionById(@Param("version") Map<String, Object> version);
}
