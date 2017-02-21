package org.ost.gateway.filter.auth;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AuthFilter extends ZuulFilter {

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Map<String, String> headers = ctx.getZuulRequestHeaders();
		String xxx= ctx.getRequest().getHeader("Authorization");
		String token = headers.get("Authorization");
		if (StringUtils.isEmpty(token)) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("forbidden");
			ctx.setResponseStatusCode(403);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "route";
	}

}
