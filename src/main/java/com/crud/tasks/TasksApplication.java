package com.crud.tasks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
 			<li>Springdoc-openapi</li>
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

@OpenAPIDefinition(info = @Info( // URL to Swagger is http://localhost:8080/swagger-ui/
		title = "tasks REST API",
		description = "Description",
		version = "1.0",
		termsOfService = "termsOfService",
		contact = @Contact(name = "contactName"),
		license = @License(name = "license")
		))
@SpringBootApplication
public class TasksApplication {
	public static void main(String[] args) {

		SpringApplication.run(TasksApplication.class, args);
	}
}
