package org.spring.authenticationserver.models;

import java.io.Serializable;

public record AuthModel(String email, String password , String username , String profilePicture) implements Serializable {
}
