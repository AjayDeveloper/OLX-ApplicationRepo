package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class OlxMasterdata1Application {

	public static void main(String[] args) {
		SpringApplication.run(OlxMasterdata1Application.class, args);
	}

	@Bean
	public Docket getCostomizedDocket() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.olx")).paths(PathSelectors.any()).build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("MasterData REST API Documentation", "MasterData REST API documentation released by Zensar Ltd.",
				"2.2", "http://zensar.com", new Contact("Anand", "http://ajay.com", "ajay@zensar.com"), "GPL",
				"http://gpl.com", new ArrayList<VendorExtension>());
	}

	@Bean
	public ModelMapper getMoldeMapper() {
		 ModelMapper modelMapper = new ModelMapper();
		    modelMapper.getConfiguration()
		        .setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}
