package org.spring.authenticationserver.models;


public record AuthenticationResponse(String jwtToken ,String refreshToken) {
}
