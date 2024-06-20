package org.spring.apigateway.configs;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.function.ServerResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ServerResponse handleRuntimeException(RuntimeException e) {
		return ServerResponse.status(401).body(e.getMessage());
	}
}
