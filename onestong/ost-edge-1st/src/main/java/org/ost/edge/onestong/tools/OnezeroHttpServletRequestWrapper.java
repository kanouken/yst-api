package org.ost.edge.onestong.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class OnezeroHttpServletRequestWrapper extends HttpServletRequestWrapper {
	HttpServletRequest req;

	@SuppressWarnings("all")
	public OnezeroHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.req = request;
	}

	@SuppressWarnings("all")
	public Map getParameterMap() {

		Map map = new HashMap();
		Map m = req.getParameterMap();
		for (Object k : m.keySet()) {
			map.put(k, req.getParameter(k.toString()));
		}

		return map;
	}
}