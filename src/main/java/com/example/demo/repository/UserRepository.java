package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;

@Service
public interface UserRepository extends JpaRepository<User,Long>{
	
	

}
