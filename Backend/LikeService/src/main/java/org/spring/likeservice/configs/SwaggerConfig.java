package org.spring.likeservice.configs;


import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	GroupedOpenApi publicApi() {
		return GroupedOpenApi
				.builder()
				.group("public-api")
				.pathsToMatch("/api/**").build();
	}


	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Like Service API")
						.version("1.0")
						.description("Documentation Like Service API v1.0")
				);
	}
}
