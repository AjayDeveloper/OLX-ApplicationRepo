package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.entity.UserDocument;
import com.olx.repository.UserTokenRepo;
import com.olx.security.JwtUtil;
import com.olx.service.LoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	LoginService loginService;

	@Autowired
	UserTokenRepo userTokenRepo;

	@Autowired
	JwtUtil jwtUtil;

	/*
	 * @GetMapping (value="/admin") public String adminUser() { return "admin"; }
	 * 
	 */
	@PostMapping(value = "/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException ex) {

			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		String jwtToken = jwtUtil.generateTokenByUserName(authenticationRequest.getUsername());
		return new ResponseEntity<String>(jwtToken, HttpStatus.OK);

	}

	@GetMapping(value = "/validate/token", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Boolean> isValidateUser(@RequestHeader("Authorization") String authToken) {
		

		try {
			// String jwtToken = jwtUtil.generateToken(login.getUserName());
			String jwtToken = authToken.substring(7, authToken.length());

			String username = jwtUtil.extractUsername(jwtToken);
			UserDetails userdetails = userDetailsService.loadUserByUsername(username);
			boolean isValid = jwtUtil.validateToken(jwtToken, userdetails);
			if (isValid) {
				List<UserDocument> blackListTokenList = userTokenRepo.findAll();
				if (checkBlackListedToken(blackListTokenList, authToken)) {

					System.out.println("InvalidAuthTokenException");
				} else {
					return new ResponseEntity<Boolean>(isValid, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "/logout")
	public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String authToken) {

		return loginService.logout(authToken);
	}

	@GetMapping(value = "/name")
	public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authToken) {
		try {
			String token = authToken.replace("Bearer ", "");
			String username = jwtUtil.extractUsername(token);
			if (username.isEmpty()) {
				return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(username, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Registering user in the application.")
	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		User newUser = loginService.registerUser(user);
		if (newUser != null) {
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Get information of a user from the application.")
	@GetMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> getUserInfo(@RequestHeader("Authorization") String authToken) {

		ResponseEntity<Boolean> validTokenResponse = isValidateUser(authToken);
		if (Boolean.TRUE.equals(validTokenResponse.getBody())) {
			ResponseEntity<String> usernameResponse = getUsername(authToken);
			return new ResponseEntity<>(loginService.getUserInfo(usernameResponse.getBody()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	private boolean checkBlackListedToken(List<UserDocument> blackListTokenList, String authToken) {
		for (UserDocument blackListTokenDocument : blackListTokenList) {
			if (blackListTokenDocument.getToken().equalsIgnoreCase(authToken)) {
				return true;
			}
		}
		return false;
	}

}
