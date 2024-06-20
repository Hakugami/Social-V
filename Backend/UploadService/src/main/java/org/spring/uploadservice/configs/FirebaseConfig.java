package org.spring.uploadservice.configs;

import org.spring.uploadservice.services.FirebaseServicesInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {
	@Bean
	public FirebaseServicesInitializer firebaseServicesInitializer() {
		return new FirebaseServicesInitializer();
	}
}
