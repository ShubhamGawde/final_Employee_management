package com.employeemanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employeemanagement.exceptionhandler.AuthenticationExceptions;
import com.employeemanagement.serviceImpl.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtil;

	@Autowired
	private UserDetailServicesImpl userDetailsService;

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res,
			@NotNull FilterChain chain) throws ServletException, IOException, AuthenticationExceptions {
		
	
	    final String authorizationHeader = req.getHeader("Authorization");
	
		String username = null;
		String token = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {

			token = authorizationHeader.substring(7);

			try {

				username = jwtUtil.extractUsername(token);

			} catch (SignatureException e) {
				
				throw new AuthenticationExceptions("IllegalArgument passed or invalid token");

				
			} catch (ExpiredJwtException e) {
				
				throw new AuthenticationExceptions("Token is Expired");
				
			} catch (MalformedJwtException e) {
				
				throw new AuthenticationExceptions("invalid token");
		
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(token, loadUserByUsername)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						loadUserByUsername, null, loadUserByUsername.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}

		}

		chain.doFilter(req, res);

	}
}
