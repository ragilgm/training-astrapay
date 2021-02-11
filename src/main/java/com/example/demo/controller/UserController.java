package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CustomDateFormat;

@RestController
public class UserController {
	
	
	@Autowired
	private UserRepository userServices;
	
	@GetMapping(value = "/users")
	public List<User> getAllUser() {
		return userServices.findAll();
	}
	
	@PostMapping(value = "users")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user){
		
		user.setCreated_at(CustomDateFormat.dateNowString());
		user.setUpdated_at(null);
		
		return new ResponseEntity<>( userServices.save(user), HttpStatus.OK);
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
