package org.notification.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.sf.json.JSONObject;

@ConfigurationProperties(prefix = "im")
public class ImApplication {

	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}

	public String getClientIds() {
		return clientIds;
	}

	public void setClientIds(String clientIds) {
		this.clientIds = clientIds;
	}

	public String getAppkeys() {
		return appkeys;
	}

	public void setAppkeys(String appkeys) {
		this.appkeys = appkeys;
	}

	public String getClientSecrets() {
		return clientSecrets;
	}

	public void setClientSecrets(String clientSecrets) {
		this.clientSecrets = clientSecrets;
	}

	private String applications;
	private String clientIds;
	private String appkeys;
	private String clientSecrets;

	public JSONObject getProperty(String name) {
		boolean exits = false;
		int pIndex = 0;
		String[] appNames = applications.split(";");
		for (String _name : appNames) {
			if (_name.equalsIgnoreCase(name)) {
				exits = true;
				break;
			}
			pIndex++;
		}
		if (exits) {
			JSONObject property = new JSONObject();
			property.put("clientId", this.clientIds.split(";")[pIndex]);
			property.put("appkey", this.appkeys.split(";")[pIndex]);
			property.put("clientSecret", this.clientSecrets.split(";")[pIndex]);
			property.put("host", this.host);
			return property;
		} else {
			return null;
		}

	}

}