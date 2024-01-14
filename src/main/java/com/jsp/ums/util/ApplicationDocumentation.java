package com.jsp.ums.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {

	Info info() {
		return new Info().title("User Management System API")
						 .version("1.0v")
						 .description("User Management System is a RESTful API built using "
						 		    + " Spring Boot and MySQL Database").contact(contact());
	}
	
	Contact contact() {
		return new Contact().name("Devil")
				.email("sri09061998@gmail.com")
				.url("github.com/srinivas1457");
	}
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
	
}
