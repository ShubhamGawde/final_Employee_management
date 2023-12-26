package com.employeemanagement.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtUtils {
	private static final String SECRET_KEY = "secret";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {

		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails, Integer id, String role) {
		return generateToken(new HashMap<>(), userDetails, id, role);
	}

	public String generateToken(Map<String, Object> claims, UserDetails userDetails, Integer id, String role) {
		claims.put("id", id);
		claims.put("Role", role);
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).addClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(Long.MAX_VALUE))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	
	//To Get Id from Token
	public Integer getIdFromToken(String token) {
		String newToken = token.substring(7);
		return (Integer) extractAllClaims(newToken).get("id");
	}

}
