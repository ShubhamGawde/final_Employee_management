package com.employeemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.security.SecurityScheme.*;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}
	
	@Bean
	OpenAPI springShopOpenAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().
	            addList("Bearer Authentication"))
		        .components(new Components().addSecuritySchemes
		            ("Bearer Authentication", createAPIKeyScheme()))
				.info(new Info().title("EmployeeManagement API").description("Spring Employee management application")
						.version("v0.0.1").contact(new Contact().name("Shubham").email("shubhamgawde288@gmail.com"))
						.license(new License().name("Shubham").url("http://localhost:8080")));

	}

}
