package org.spring.userservice.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfigurationSource corsConfigurationSource;

	private static void configureCsrf(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
	}

	private static void configureSessionManagement(HttpSecurity http) throws Exception {
		http.sessionManagement(s -> s
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
	}

	private static void configureUrlBasedCors(HttpSecurity http) throws Exception {
		//TODO: Remember to change the port number!!
		http.cors(c -> {
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true);
			config.addAllowedOrigin("http://localhost:4200"); // Angular app url
			config.addAllowedHeader("*");
			config.addAllowedMethod("*");
			source.registerCorsConfiguration("/**", config);
			c.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		});
	}

	private static void configurePermits(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
						.anyRequest().permitAll()
		);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		configurePermits(http);
		configureCsrf(http);
		http.cors(Customizer.withDefaults());
		configureSessionManagement(http);
		return http.build();

	}
}
