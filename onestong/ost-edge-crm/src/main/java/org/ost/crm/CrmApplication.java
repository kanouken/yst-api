package org.ost.crm;

import java.io.IOException;

import org.ost.crm.conf.FeignDecoder;
import org.ost.crm.conf.FeignEncoder;
import org.ost.crm.interceptor.AuthCheckInterceptor;
import org.ost.crm.interceptor.CrossDomainInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author xnq
 *
 */

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients
public class CrmApplication extends WebMvcConfigurerAdapter {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public FeignDecoder feignDecoder(){
		return new FeignDecoder();
	}

	@Bean
	public FeignEncoder feignEncoder(){
		return new FeignEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		CrossDomainInterceptor cd = new CrossDomainInterceptor();
		registry.addInterceptor(cd).addPathPatterns("/**");
		AuthCheckInterceptor auth = new AuthCheckInterceptor();

		registry.addInterceptor(auth).addPathPatterns("/**").excludePathPatterns(
				"/swagger**",
				"/configuration/**",
				"/v2/api**",
				"/info",
				"/report/*/export",
				"/api/users/login");
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

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(testApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.ost.crm.controller")).paths(PathSelectors.any()).build();
	}

	private ApiInfo testApiInfo() {
		Contact contact = new Contact("auth", "name", "email");
		ApiInfo apiInfo = new ApiInfo("", // 大标题
				"api接口", // 小标题
				"0.1", // 版本
				"", contact, // 作者
				"", // 链接显示文字
				""// 网站链接
		);
		return apiInfo;
	}

}
