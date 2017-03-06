package org.ost.gateway.filter.auth;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.ost.gateway.properties.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends ZuulFilter {
	@Autowired
	GatewayProperties gw;

	@Override
	public Object run() {
		ObjectMapper mapper = new ObjectMapper();
		RequestContext ctx = RequestContext.getCurrentContext();
		final String authHeader = ctx.getRequest().getHeader("Authorization");
		ctx.getRequest().getRemoteHost();
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// 无token
//			ctx.setSendZuulResponse(false);
//			OperateResult op = new OperateResult("", "forbidden", "");
//			op.setStatusCode("500");
//			try {
//				ctx.setResponseBody(mapper.writeValueAsString(op));
//			} catch (JsonProcessingException e) {
//			}
//			return null;
		}
		// token
//		String token = null;
//		token = authHeader.substring(7);
//		try {
//			Claims body = Jwts.parser().requireSubject("server").setSigningKey(gw.getJwtPrivatekey())
//					.parseClaimsJws(token).getBody();
//			Integer userId = (Integer) body.get("userId");
//			Integer tenantId = (Integer) body.get("tenantId");
//			String realName = (String) body.get("realName");
//			ctx.getRequest().setAttribute("userId", userId);
//			ctx.getRequest().setAttribute("tenantId", tenantId);
//			ctx.getRequest().setAttribute("realName", realName);
//		} catch (Exception e) {
//			ctx.setSendZuulResponse(false);
//			OperateResult op = new OperateResult("", "forbidden", "");
//			op.setStatusCode("500");
//			try {
//				ctx.setResponseBody(mapper.writeValueAsString(op));
//			} catch (JsonProcessingException e1) {
//			}
//		}
		ctx.getRequest().setAttribute("userId", "sssxx");
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
