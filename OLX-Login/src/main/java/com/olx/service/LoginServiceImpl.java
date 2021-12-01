package com.olx.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olx.dto.User;
import com.olx.repository.UserRepository;
import com.olx.util.LoginConverterUtil;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserRepository userRepository;



	@Autowired
    ModelMapper modelMapper;


	@Autowired
	PasswordEncoder passwordEncoder;



	@Override
	public boolean logout(String username) {
		
		return true;
	}

	@Override
	public User registerUser(User user) {
		try {
			//user.setRoles("ROLE_USER");
			user.setRoles(user.getRoles());
			return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.save(LoginConverterUtil.convertDtoToEntity(modelMapper, user)));
			} catch (Exception e) {
			return null;
			}
		
	}

	@Override
    public List<User> getUserInfo(String username) {
        return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.findByUsername(username));
    }

}
