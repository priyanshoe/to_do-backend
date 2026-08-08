package com.practice.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.todo.entity.User;
import com.practice.todo.services.UserServices;

@RestController
@RequestMapping("api/auth")
public class UserController {
	
	@Autowired
	private UserServices userServices;
	
	@GetMapping("/helo")
	public String getUser() {
		return "User heloo";
	}
	
	// user sign up
	@PostMapping("/sign-up")
	public User registerUser(@RequestBody User user) {
		return userServices.register(user);
	}
	
	// user sign in
	@PostMapping("/sign-in")
	public String loginUser(@RequestBody User user) {
		return userServices.login(user);
	}
}
