package org.notification.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.sf.json.JSONObject;

@ConfigurationProperties(prefix = "getui")
public class GetuiApplication {

	private String host;
	private String applications;
	private String appids;
	private String appkeys;
	private String masters;

	public String getHost() {
		return host;
	}

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
			property.put("appid", this.appids.split(";")[pIndex]);
			property.put("appkey", this.appkeys.split(";")[pIndex]);
			property.put("master", this.masters.split(";")[pIndex]);
			property.put("host", this.host);
			return property;
		} else {
			return null;
		}

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

	public String getAppids() {
		return appids;
	}

	public void setAppids(String appids) {
		this.appids = appids;
	}

	public String getAppkeys() {
		return appkeys;
	}

	public void setAppkeys(String appkeys) {
		this.appkeys = appkeys;
	}

	public String getMasters() {
		return masters;
	}

	public void setMasters(String masters) {
		this.masters = masters;
	}
}
