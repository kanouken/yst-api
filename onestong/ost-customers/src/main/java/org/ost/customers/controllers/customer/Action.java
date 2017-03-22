package org.ost.customers.controllers.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class Action {
	public static final String PAGE_CURRENT = "curPage";
	public static final String PAGE_CURRENT_DEFAULT = "1";
	public static final String PAGE_PER_SIZE = "perPageSum";
	public static final String PAGE_PER_SIZE_DEFAULT = "20";
	
	public static final String TENANT_ID = "schemaID";
	
	public Map<String, Object> getRequestParam(HttpServletRequest request){
		Map<String, Object> params = new HashMap<>();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Map.Entry<String, String[]>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String[]> entry = it.next();
			String key = (String) entry.getKey();
			String[] valueObj = entry.getValue();
			String value = "";
			if (valueObj != null && valueObj.length > 0)
				value = valueObj[0];
			if (StringUtils.isNotEmpty(value)){
				params.put(key, value);
			}
		}
		return params;
	}
}
