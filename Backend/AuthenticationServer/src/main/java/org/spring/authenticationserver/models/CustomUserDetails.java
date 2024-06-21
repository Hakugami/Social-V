package org.spring.authenticationserver.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record CustomUserDetails(AuthModel user) implements UserDetails, Serializable {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return user.password();
	}

	@Override
	public String getUsername() {
		return user.email();
	}

	public String getProfilePicUrl() {
		return user.profilePicture();
	}

	public String getEmail() {
		return user.email();
	}

	public String getUsernameField() {
		return user.username();
	}
}
