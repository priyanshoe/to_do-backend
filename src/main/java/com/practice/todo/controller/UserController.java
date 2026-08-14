package com.practice.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.todo.DTO.ApiResponse;
import com.practice.todo.entity.User;
import com.practice.todo.services.UserServices;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserServices userServices;
	
	// user sign up
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
		return userServices.register(user);
	}
	
	// user sign in
	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponse> loginUser(@RequestBody User user) {
			 return userServices.login(user.getEmail(), user.getPassword());
	}
}
