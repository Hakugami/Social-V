package org.spring.userservice.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static void configureCsrf(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
	}

	private static void configureSessionManagement(HttpSecurity http) throws Exception {
		http.sessionManagement(s -> s
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
	}

	private static void configureUrlBasedCors(HttpSecurity http) throws Exception {
		http.cors(c -> {
		}); // Disable CORS
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
		configureUrlBasedCors(http);
		configureSessionManagement(http);
		return http.build();

	}
}
