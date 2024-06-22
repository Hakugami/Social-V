package org.spring.authenticationserver.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.authenticationserver.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		log.info("Filtering request: {}", request.getRequestURI());
		final String authorizationHeader = request.getHeader("Authorization");
		log.info("Authorization header: {}", authorizationHeader);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			log.info("Validating token");
			String jwt = jwtUtil.removeBearer(authorizationHeader);
			try {
				boolean b = validateAndSetAuthentication(jwt, request);
				if (!b) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
					return;
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	public boolean validateAndSetAuthentication(String jwt, HttpServletRequest request) {
		String username = jwtUtil.getUsernameFromToken(jwt);
		log.info("Username from token: {}", username);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			log.info("User details: {}", userDetails);

			if (jwtUtil.validateToken(jwt, userDetails)) {
				log.info("Token is valid");
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				return true;
			}
		}
		log.info("Token is invalid");
		return false;
	}
}
