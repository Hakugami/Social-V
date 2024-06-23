package org.spring.userservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegistrationEvent implements Serializable {
	private String id;
	private String username;
	private String email;
	private String profilePicture;
	private String firstName;
	private String lastName;
}
