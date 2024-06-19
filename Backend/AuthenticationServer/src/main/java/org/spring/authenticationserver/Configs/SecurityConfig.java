package org.spring.authenticationserver.Configs;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationserver.filters.JwtRequestFilter;
import org.spring.authenticationserver.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

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
						.requestMatchers("/auth/login").permitAll()
						.anyRequest().authenticated()
		);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		configurePermits(http);
		configureCsrf(http);
		configureUrlBasedCors(http);
		configureSessionManagement(http);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}