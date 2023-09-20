package com.employeemanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employeemanagement.service.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

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
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
			@NotNull FilterChain chain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		final String username;
		final String jwtToken;

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		jwtToken = authorizationHeader.substring(7);
		try {
			username = this.jwtUtil.extractUsername(jwtToken);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				if (this.jwtUtil.validateToken(jwtToken, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException ex) {

			ex.printStackTrace();
			System.out.println("Token is expired");

		} 
			//catch (Exception ex) {
//			ex.printStackTrace();
//			System.out.println(ex.getMessage());
//
//		}
	}

}
