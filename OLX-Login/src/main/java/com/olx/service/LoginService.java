package com.olx.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.olx.dto.User;

public interface LoginService {
	
	ResponseEntity<Boolean> logout(String authToken);
	User registerUser(User user);
	List<User>getUserInfo(String username);



}
