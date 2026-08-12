package com.practice.todo.services;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.todo.entity.User;
import com.practice.todo.repository.UserRepository;
import com.practice.toto.DTO.ApiResponse;

@Service
public class UserServices {
	
	@Autowired
	private  UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	
	// REGISTRATION
	public ResponseEntity<ApiResponse> register(User user) {
		Optional<User> isExsistUser = userRepo.findByEmail(user.getEmail());
		if(isExsistUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("user already exisit", isExsistUser.get()));
		}
		user.setPassword(encoder.encode(user.getPassword()));
		User newUser = userRepo.save(user);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(newUser.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("user regestered succesfully", jwtService.generateToken(userDetails)));
	}
	
	
	//	login
	public ResponseEntity<ApiResponse> login(String email, String password) {
		
		try {
	
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("user login successfull", jwtService.generateToken(userDetails)));
		
		}catch (BadCredentialsException e) {
			return ResponseEntity
	                .status(HttpStatus.UNAUTHORIZED)
	                .body(new ApiResponse("bad credentials", null));
		}
	}
	
}






