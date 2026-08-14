package com.practice.todo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.todo.DTO.ApiResponse;
import com.practice.todo.services.CustomUserDetailsService;
import com.practice.todo.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
			) throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		String jwt = null;
		String username = null;
		
		// check Auth header
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			
			try {
				username = jwtService.extractUsername(jwt);
			} catch (Exception e) {
				ApiResponse apiResponse = new ApiResponse("Invalid token", null);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write(
						objectMapper.writeValueAsString(apiResponse)
						);
				return;
			}
		}
		
		// Auth the user
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(jwtService.isTokenValid(jwt, userDetails)) {
				
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
				
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
						);
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
