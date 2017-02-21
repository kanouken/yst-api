package org.ost.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gw")
public class GatewayProperties {
	private String jwtPrivatekey;

	public String getJwtPrivatekey() {
		return jwtPrivatekey;
	}

	public void setJwtPrivatekey(String jwtPrivatekey) {
		this.jwtPrivatekey = jwtPrivatekey;
	}

}
