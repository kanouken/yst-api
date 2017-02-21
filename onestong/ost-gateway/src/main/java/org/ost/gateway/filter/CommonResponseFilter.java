package org.ost.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.common.tools.OperateResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class CommonResponseFilter extends ZuulFilter {

	@Override
	public Object run() {
		ObjectMapper mapper = new ObjectMapper();
		RequestContext ctx = RequestContext.getCurrentContext();
		try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
			if (responseDataStream != null) {

				final String responseData = IOUtils.toString(new InputStreamReader(responseDataStream, "UTF-8"));
				int statusCode = ctx.getResponseStatusCode();
				ctx.setResponseStatusCode(200);
				if (statusCode == 200) {
					StringBuilder sb = new StringBuilder("{");
					sb.append("\"").append("innerException").append("\"")
					.append(":").append("\"\"").append(",")
					.append("\"").append("statusCode").append("\"").append(":")
					.append("\"").append("200").append("\"").append(",")
					.append("\"").append("description").append("\"").append(":")
					.append("\"").append("\"").append(",")
					.append("\"").append("data").append("\"").append(":");
					if(responseData.contains("{")){
						sb.append(responseData);
					}else{
						sb.append("\"").append(responseData).append("\"");
					}
					sb.append("}");
					ctx.setResponseBody(sb.toString());
				} else if (String.valueOf(statusCode).startsWith("4")) {
					OperateResult op = new OperateResult();
					op.setData("");
					op.setDescription(responseData);
					op.setInnerException("");
					ctx.setResponseBody(mapper.writeValueAsString(op));
				} else {
					OperateResult op = new OperateResult();
					op.setData("");
					op.setDescription("");
					op.setInnerException(responseData);
					ctx.setResponseBody(mapper.writeValueAsString(op));
				}

			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 5000;
	}

	@Override
	public String filterType() {
		return "route";
	}

}
