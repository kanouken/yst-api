package org.ost.entity.tools;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonType {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertToMap(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			if (obj instanceof String) {
				return mapper.readValue(obj.toString(), Map.class);
			} else {
				byte[] b = (byte[]) obj;
				return mapper.readValue(b, Map.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
