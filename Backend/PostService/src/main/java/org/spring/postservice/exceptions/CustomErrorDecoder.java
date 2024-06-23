package org.spring.postservice.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		HttpStatus status = HttpStatus.resolve(response.status());
		if (status != null) {
			switch (status) {
				case NOT_FOUND:
					return new CustomResourceNotFoundException("Resource not found");
				// Add cases for other status codes as needed
				default:
					return new ResponseStatusException(status, "Client error: " + response.reason());
			}
		}
		return new Exception("Generic error: " + response.reason());
	}
}


