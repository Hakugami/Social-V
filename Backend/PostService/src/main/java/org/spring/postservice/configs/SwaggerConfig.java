package org.spring.postservice.configs;


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
						.title("Post Service API")
						.version("1.0")
						.description("Documentation Post Service API v1.0")
				);
	}
}
