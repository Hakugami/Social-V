package org.spring.FriendService.models.dtos;

import java.io.Serializable;
import java.time.LocalDate;

public record UserModelDTO(String username, String email, String firstName, String lastName,
                        String country, String city, LocalDate birthDate,
                        String profilePicture, String url) implements Serializable {
}