package com.practice.todo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain secutiyFilterChain(
			HttpSecurity http) throws Exception{
		http
		.csrf(csrf-> csrf.disable())
//		.httpBasic(httpBasic -> {})
		.cors(cors -> {})
		.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		.authorizeHttpRequests(auth-> auth
			.requestMatchers("/api/auth/**","/health").permitAll()
			.requestMatchers("/api/task/user/**").hasAnyRole("USER","ADMIN")
			.requestMatchers("/api/task/*").hasRole("ADMIN")
			.anyRequest().authenticated())
		
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	// If you want to auth locally 
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withUsername("Priyanshu").password("EncodedPassRequired").roles("USER").build();
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOrigins(
	        List.of("http://localhost:3000")
	    );

	    configuration.setAllowedMethods(
	        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
	    );

	    configuration.setAllowedHeaders(
	        List.of("*")
	    );

	    UrlBasedCorsConfigurationSource source =
	        new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration(
	        "/**",
	        configuration
	    );

	    return source;
	}
	
}
