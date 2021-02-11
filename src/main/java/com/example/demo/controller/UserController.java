package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.MyUserDetailServices;
import com.example.demo.configuration.SecurityUtility;
import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CustomDateFormat;
import com.example.demo.util.JWTUtil;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailServices userDetailServices;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	private UserRepository userServices;
	
	@GetMapping(value = "/users")
	public List<User> getAllUser() {
		return userServices.findAll();
	}
	
	@PostMapping(value = "/registration")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user){
		user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
		user.setCreated_at(CustomDateFormat.dateNowString());
		user.setUpdated_at(null);
		
		return new ResponseEntity<>( userServices.save(user), HttpStatus.OK);
	}
	
	@PostMapping(value = "/authentication")
	public ResponseEntity<?> loginUser(@Valid @RequestBody AuthenticationRequest user) throws Exception{
		
		
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails= userDetailServices.loadUserByUsername(user.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		System.out.println(jwtUtil.validateToken(jwt, userDetails));
		System.out.println(jwtUtil.isTokenExpired(jwt));
		System.out.println(jwtUtil.extractAllClaims(jwt));
		System.out.println(jwtUtil.extractExpiration(jwt));
		
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}
	
	
	
	@PutMapping
	public User updateUser(@PathVariable("id") long id, @RequestBody User user) {
		User updateUser = userServices.getOne(id);
		updateUser.setUsername(user.getUsername());
		updateUser.setPassword(user.getPassword());
		updateUser.setUpdated_at(CustomDateFormat.dateNowString());
		return userServices.save(updateUser);
	}

}
