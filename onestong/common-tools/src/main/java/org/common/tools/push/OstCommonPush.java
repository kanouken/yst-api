package org.common.tools.push;

import java.io.IOException;

/**
 * 推送工具类 默认使用 个推 服务
 * 
 * @see https://getui.com
 * @author xnq
 *
 */
public interface OstCommonPush {
	
	/**
	 * 
	 * @param content
	 * @param payLoadBody
	 * @param clientId
	 * @return 
	 * @throws IOException 
	 */
	public String apnPush(String content, String payLoadBody, String clientId) throws IOException;

	
	
	
}
