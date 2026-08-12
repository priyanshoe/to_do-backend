package com.practice.todo.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public static final String SECRET_KEY = "my-super-secret-key-for-jwt-authentication-123456789";
	public final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1hour
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(
						new Date(System.currentTimeMillis()+ EXPIRATION_TIME)
						)
				.signWith(key)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpire(token);
	}
	
	public boolean isTokenExpire(String token) {
		return extractAllClaims(token)
				.getExpiration()
				.before(new Date());
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
