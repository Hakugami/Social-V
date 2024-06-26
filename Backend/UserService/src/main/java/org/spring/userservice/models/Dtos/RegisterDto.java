package org.spring.userservice.models.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.spring.userservice.models.UserModel;

import java.io.Serializable;

/**
 * DTO for {@link UserModel}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto implements Serializable {
	@NotEmpty(message = "username can't be empty")
	String username;
	@NotEmpty(message = "password can't be empty")
	@Length(min = 8)
	String password;
	@Email
	String email;
	@NotEmpty(message = "first name can't be empty")
	String firstName;
	@NotEmpty(message = "last name can't be empty")
	String lastName;

}