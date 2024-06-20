package org.spring.authenticationserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

	@Value("${jwt.expiration}")
	private long expirationTime;

	@Value("${jwt.refresh.expiration}")
	private long refreshExpirationTime;

	private PrivateKey privateKey;
	private PublicKey publicKey;

	@PostConstruct
	public void init() {
		try {
			// Load private key
			ClassPathResource privateResource = new ClassPathResource("private_key.pem");
			byte[] privateBytes = Files.readAllBytes(privateResource.getFile().toPath());
			privateKey = getPrivateKey(privateBytes);

			// Load public key
			ClassPathResource publicResource = new ClassPathResource("public_key.pem");
			byte[] publicBytes = Files.readAllBytes(publicResource.getFile().toPath());
			publicKey = getPublicKey(publicBytes);

			log.info("RSA keys loaded successfully");

		} catch (IOException e) {
			log.error("Failed to load RSA keys", e);
		}
	}

	private PrivateKey getPrivateKey(byte[] keyBytes) {
		try {
			String privateKeyPEM = new String(keyBytes)
					.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\\s+", "");
			byte[] decoded = java.util.Base64.getDecoder().decode(privateKeyPEM);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(spec);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get private key", e);
		}
	}

	private PublicKey getPublicKey(byte[] keyBytes) {
		try {
			String publicKeyPEM = new String(keyBytes)
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "")
					.replaceAll("\\s+", "");
			byte[] decoded = java.util.Base64.getDecoder().decode(publicKeyPEM);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(spec);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get public key", e);
		}
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities());
		return createToken(claims, userDetails.getUsername(), expirationTime);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities());
		return createToken(claims, userDetails.getUsername(), refreshExpirationTime);
	}

	private String createToken(Map<String, Object> claims, String subject, long expiration) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(privateKey, SignatureAlgorithm.RS512)
				.compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(publicKey)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			log.error("Failed to parse claims from token", e);
			return Jwts.claims(Collections.emptyMap());
		}
	}

	public boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	// Refresh token method
	public String refreshToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return createToken(claims, claims.getSubject(), expirationTime);
	}

	public String removeBearer(String token) {
		if (token==null || !token.startsWith("Bearer ")) {
			throw new RuntimeException("Invalid token");
		}
		return token.substring(7);
	}
}
