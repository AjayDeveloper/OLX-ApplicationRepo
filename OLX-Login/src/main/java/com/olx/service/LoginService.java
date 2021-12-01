package com.olx.service;

import java.util.List;

import com.olx.dto.User;

public interface LoginService {
	
	boolean logout(String username);
	User registerUser(User user);
	List<User>getUserInfo(String username);



}
