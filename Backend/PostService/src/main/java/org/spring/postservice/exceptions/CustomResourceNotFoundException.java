package org.spring.postservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomResourceNotFoundException extends ResponseStatusException {
	public CustomResourceNotFoundException(String reason) {
		super(HttpStatus.NOT_FOUND, reason);
	}
}
