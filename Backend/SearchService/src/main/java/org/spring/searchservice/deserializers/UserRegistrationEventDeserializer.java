package org.spring.searchservice.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.spring.searchservice.events.UserRegistrationEvent;

import java.io.IOException;
import java.util.Map;

public class UserRegistrationEventDeserializer implements Deserializer<UserRegistrationEvent> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// No additional configuration needed
	}

	@Override
	public UserRegistrationEvent deserialize(String topic, byte[] data) {
		if (data == null) {
			return null;
		}
		try {
			return objectMapper.readValue(data, UserRegistrationEvent.class);
		} catch (IOException e) {
			throw new RuntimeException("Error deserializing JSON to UserRegistrationEvent: " + e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		// No resources to close
	}
}

