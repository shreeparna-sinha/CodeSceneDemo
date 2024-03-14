package com.psl.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


/**
* Main class for the QuizMaster Application.
* Configures and launches the Spring Boot application.
*
* 
*/
@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "api_key", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "QuizMaster Application", version = "0.0.1-SNAPSHOT"), security = { @SecurityRequirement(name = "api_key") })
public class QuizMasterApplication {

	
	/**
	* Main method to start the QuizMaster Application.
	*
	* @param args Command line arguments.
	*/
	public static void main(String[] args) {
		SpringApplication.run(QuizMasterApplication.class, args);
	}

}
