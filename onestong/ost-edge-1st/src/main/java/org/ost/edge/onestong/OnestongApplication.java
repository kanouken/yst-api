package org.ost.edge.onestong;

import java.io.IOException;

import org.ost.edge.onestong.inteceptor.AuthCheckInterceptor;
import org.ost.edge.onestong.inteceptor.CrossDomainInterceptor;
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
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
public class OnestongApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(OnestongApplication.class, args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		CrossDomainInterceptor cd = new CrossDomainInterceptor();
		registry.addInterceptor(cd).addPathPatterns("/**");
		AuthCheckInterceptor auth = new AuthCheckInterceptor();

		registry.addInterceptor(auth).addPathPatterns("/**").excludePathPatterns("/swagger**", "/configuration/**",
				"/v2/api**", "/info", "/api/users/login","common/auth/**");
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
				.apis(RequestHandlerSelectors.basePackage("org.ost.edge.onestong.controller.api"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo testApiInfo() {
		Contact contact = new Contact("auth", "name", "email");
		ApiInfo apiInfo = new ApiInfo("1st 事件", // 大标题
				"api接口", // 小标题
				"0.1", // 版本
				"", contact, // 作者
				"", // 链接显示文字
				""// 网站链接
		);
		return apiInfo;
	}

}
