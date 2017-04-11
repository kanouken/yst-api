package com.oz.onestong;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.oz.onestong.interceptor.AdminLoginRequiredIntercepter;
import com.oz.onestong.interceptor.ApplicationIntercepter;
import com.oz.onestong.interceptor.AuthCheckInterceptor;
import com.oz.onestong.interceptor.LoginRequiredIntercepter;
import com.oz.onestong.interceptor.PagedInterceptor;


/**
 * 
 * @author xnq
 *
 */

@SpringBootApplication
 @EnableEurekaClient
@EnableFeignClients
public class App extends WebMvcConfigurerAdapter {
	@Value("${pathStrip}")
	private String pathStrip;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		ApplicationIntercepter webApp = new ApplicationIntercepter();
		webApp.setPathStrip(this.pathStrip);
		registry.addInterceptor(webApp).addPathPatterns("/**");
		PagedInterceptor page = new PagedInterceptor();
		registry.addInterceptor(page).addPathPatterns("/**");

		LoginRequiredIntercepter loginIntercepter = new LoginRequiredIntercepter();
		registry.addInterceptor(loginIntercepter).addPathPatterns("/**").excludePathPatterns("/index", "/login",
				"/resources/**", "/ost/**");

		AdminLoginRequiredIntercepter adminLoginRequiredIntercepter = new AdminLoginRequiredIntercepter();

		registry.addInterceptor(adminLoginRequiredIntercepter).addPathPatterns("/ost/**")
				.excludePathPatterns("/ost/admin/login", "/ost/admin/doLogin", "/resources/**");

		AuthCheckInterceptor authCheckInterceptor = new AuthCheckInterceptor();
		registry.addInterceptor(authCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/index", "/login",
				"/logout", "/ost/**", "/resources/**");
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
		om.configure(Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
					throws IOException, JsonProcessingException {
				jg.writeString("");
			}
		});
		return om;
	}

}
