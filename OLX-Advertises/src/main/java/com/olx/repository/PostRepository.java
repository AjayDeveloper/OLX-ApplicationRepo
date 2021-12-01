package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

	List<PostEntity> findByTitle(String title);

}
