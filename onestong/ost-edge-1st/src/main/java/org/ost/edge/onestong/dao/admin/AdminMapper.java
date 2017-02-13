package org.ost.edge.onestong.dao.admin;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
	Map<String, Object> selectOneByLoginName(@Param("loginName") String loginName);
}
