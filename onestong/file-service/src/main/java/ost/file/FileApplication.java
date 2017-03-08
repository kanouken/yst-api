package ost.file;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import ost.file.properties.OstFileProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(value = { OstFileProperties.class })
public class FileApplication {

}
