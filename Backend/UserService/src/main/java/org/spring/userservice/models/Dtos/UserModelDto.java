package org.spring.userservice.models.Dtos;

import jakarta.validation.constraints.Email;
import org.spring.userservice.models.Gender;
import org.spring.userservice.models.Status;
import org.spring.userservice.models.UserModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link UserModel}
 */
public record UserModelDto(String username, @Email String email, Status status, String firstName, String lastName,
                           String address, Gender gender, String country, String city, LocalDate birthDate,
                           String phoneNumber, String profilePicture, String coverPicture,
                           String url) implements Serializable {
}