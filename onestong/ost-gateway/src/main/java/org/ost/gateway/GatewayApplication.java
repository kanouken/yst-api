package org.ost.gateway;

import org.ost.gateway.filter.CommonResponseFilter;
import org.ost.gateway.filter.auth.AuthFilter;
import org.ost.gateway.properties.GatewayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableConfigurationProperties(value = { GatewayProperties.class })
public class GatewayApplication {

	@Bean
	public CommonResponseFilter commonResponseFilter() {
		return new CommonResponseFilter();
	}

	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
