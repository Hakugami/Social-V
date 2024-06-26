package org.spring.messageservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder()
				.group("public-api")
				.pathsToMatch("/api/**")
				.build();
	}




	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Message Service API")
						.version("1.0")
						.description("Message Service API v1.0")
				);
	}
}
