package org.spring.postservice.configs;

import org.spring.postservice.exceptions.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfiguration {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}

