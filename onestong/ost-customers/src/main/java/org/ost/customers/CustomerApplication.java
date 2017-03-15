package org.ost.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * test
 * @author xnq
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class CustomerApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
		om.configure(Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		return om;
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.ost.customers.controllers")).paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("auth", "name", "email");
		ApiInfo apiInfo = new ApiInfo("", "api接口", "2.1", "", contact, "", "");
		return apiInfo;
	}

}
