package com.practice.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.todo.entity.User;
import com.practice.todo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User authUser = userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+" User not found"));
		return org.springframework.security.core.userdetails.User
				.withUsername(authUser.getEmail())
				.password(authUser.getPassword())
				.roles(authUser.getRole().name())
				.build();
		}

}
