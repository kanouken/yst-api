package com.oz.onestong.dao.base;

import java.util.Map;

public interface BaseDAO {
	public Map<String, Object> getAuto_incrementID(String tableName);
}
