package com.crud.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 The Attributes of the Application:
 <ul>
 	<li>Database used: MySql</li>
 	<li>Libraries used:
 		<ul>
 			<li>Spring Boot</li>
 			<li>Lombok</li>
    		<li>JUnit 5</li>
 			<li>MySQL Connector/J </li>
 			<li>Springfox</li>
 			<li>SLF4J</li>
 		</ul>
 	</li>
 	<li>HTTP methods used:
 		<ul>
 			<li>GET</li>
 			<li>POST</li>
 			<li>PUT</li>
 			<li>DELETE</li>
 		</ul>
 	</li>
 </ul>
 */

@SpringBootApplication
@EnableSwagger2 // Annotation is used to enable the Swagger2 for your Spring Boot application
public class TasksApplication {

	public static void main(String[] args) {

		SpringApplication.run(TasksApplication.class, args);

	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfo(
						"tasks REST API",
						"Description",
						"version",
						"termsOfServiceUrl",
						"contactName",
						"licene",
						"licenceUrl"
						));
	}
// URL to Swagger is http://localhost:8080/swagger-ui/
}
