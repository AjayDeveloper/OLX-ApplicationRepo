package com.olx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecuirtyConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception{ // Authorization
		http.csrf().disable()
	       	.authorizeRequests()
		    .antMatchers("/user/authenticate").permitAll()
		    .and()
		    .formLogin(); //default login page
		    
		
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception { //Authentication 
	
	  	
		auth.userDetailsService(userDetailsService);
		
		
		/*
		 * auth.inMemoryAuthentication()
		 * .withUser("tom").password(getPasswordEncoder().encode("tom123")).roles(
		 * "USER") .and()
		 * .withUser("jerry").password(getPasswordEncoder().encode("jerry123")).roles(
		 * "ADMIN");
		 * 
		 */
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
	
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}