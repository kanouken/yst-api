package org.common.tools.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 短信发送工具类
 * 
 * @see spring cloud
 * @see spring resttemplate
 * @author xnq
 *
 */
public class SmsUtil {
	private Logger log = LoggerFactory.getLogger(SmsUtil.class);

	private RestTemplate client;

	private String application;

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public RestTemplate getClient() {
		return client;
	}

	/**
	 * <li>使用rest template 访问 common push 服务</li>
	 * 
	 * @param client
	 */
	public void setClient(RestTemplate client) {
		this.client = client;
	}

	public String getApplication() {
		return application;
	}

	/**
	 * 指定推送使用的应用配置
	 * 
	 * @param application
	 * @see common application.properties setting
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * 
	 * @param content
	 *            消息内容
	 * @param payLoadBody
	 *            透传消息
	 * @param cids
	 *            client id 数组
	 */
	public void apnPush(String content, String payLoadBody, List<String> cids) {
		if (client == null) {
			log.warn("please instance the resttemplate first");
		}
		ObjectMapper mapper = new ObjectMapper();
		String url = "http://commonService/getui/single";
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("content", content);
		params.put("payLoadBody", payLoadBody);
		params.put("cids", cids);
		params.put("application", application);
		HttpEntity<String> formEntity = null;
		try {
			formEntity = new HttpEntity<String>(mapper.writeValueAsString(params), headers);
			ResponseEntity<String> output = client.postForEntity(url, formEntity, String.class);
			if (HttpStatus.OK != output.getStatusCode()) {
				log.error("push failed " + output.getBody());
			}
		} catch (JsonProcessingException e) {
			log.error("json serializate error :" + e.getMessage());
		}
	}
}
