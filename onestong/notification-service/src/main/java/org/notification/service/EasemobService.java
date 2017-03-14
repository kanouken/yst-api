package org.notification.service;

import org.common.tools.exception.ApiException;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.notification.model.EasemobUser;
import org.notification.properties.ImApplication;
import org.notification.service.easemob.HTTPMethod;
import org.notification.service.easemob.Roles;
import org.notification.service.easemob.vo.ClientSecretCredential;
import org.notification.service.easemob.vo.Credential;
import org.notification.tools.JerseyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.sf.json.JSONObject;

@Service
public class EasemobService extends BaseService {

	private JsonNodeFactory factory = new JsonNodeFactory(false);
	@Autowired
	private ImApplication application;

	/**
	 * 注册IM用户[单个]
	 * 
	 * 给指定AppKey创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	private ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

		JerseyClient CLIENT = JerseyUtils.getJerseyClient(true);
		// 通过app的client_id和client_secret来获取app管理员token
		String name = dataNode.get("application").asText();
		JSONObject property = this.application.getProperty(name);
		if (null == property) {
			throw new IllegalArgumentException("can not find property in the application.yml !");
		}
		JerseyWebTarget ROOT_TARGET = CLIENT.target("https://" + property.getString("host") + "/");
		JerseyWebTarget APPLICATION_TEMPLATE = ROOT_TARGET.path("{org_name}").path("{app_name}");

		JerseyWebTarget TOKEN_APP_TARGET = APPLICATION_TEMPLATE.path("token");
		JerseyWebTarget USERS_TARGET = APPLICATION_TEMPLATE.path("users");
		String appkey = property.getString("appkey");
		Credential credential = new ClientSecretCredential(property.getString("clientId"),
				property.getString("clientSecret"), Roles.USER_ROLE_APPADMIN, appkey, TOKEN_APP_TARGET);
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", appkey)) {
			logger.error("Bad format of Appkey: " + appkey);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		objectNode.removeAll();
		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			logger.error("Property that named username must be provided .");
			objectNode.put("message", "Property that named username must be provided .");
			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			logger.error("Property that named password must be provided .");
			objectNode.put("message", "Property that named password must be provided .");
			return objectNode;
		}
		try {
			JerseyWebTarget webTarget = null;
			webTarget = USERS_TARGET.resolveTemplate("org_name", appkey.split("#")[0]).resolveTemplate("app_name",
					appkey.split("#")[1]);
			objectNode = JerseyUtils.sendRequest(webTarget, dataNode, credential, HTTPMethod.METHOD_POST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectNode;
	}

	public boolean createUser(EasemobUser user) {
		boolean flag = false;
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username", user.getAccount());
		datanode.put("password", user.getPassword());
		datanode.put("nickname", user.getName());
		datanode.put("application", user.getApplication());
		ObjectNode resultNode = this.createNewIMUserSingle(datanode);
		if (null != resultNode && "200".equals(resultNode.get("statusCode").toString())) {
			flag = true;
		} else {
			throw new ApiException("环信用户创建失败", resultNode.get("error_description").asText());
		}
		return flag;

	}
}
