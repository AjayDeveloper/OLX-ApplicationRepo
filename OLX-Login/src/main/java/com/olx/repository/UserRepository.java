package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	    List<UserEntity> findByUsername(String username);
	  

}
