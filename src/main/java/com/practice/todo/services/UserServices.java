package com.practice.todo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.todo.entity.User;
import com.practice.todo.repository.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private  UserRepository userRepo;

	
	// REGISTRATION
	public User register(User user) {
		return userRepo.save(user);
	}
	
	public String login(User user) {
		Optional<User> authUser = userRepo.findByEmail(user.getEmail());
		if(authUser.isEmpty()) {
			return "User account not found";
		}else if(!authUser.get().getPassword().equals(user.getPassword())) {
			return "Incorrect password";
		}
		return "Login succes";
	}
}
