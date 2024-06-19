package org.spring.authenticationserver.models;



public record AuthenticationRequest(String email, String password) {
}
