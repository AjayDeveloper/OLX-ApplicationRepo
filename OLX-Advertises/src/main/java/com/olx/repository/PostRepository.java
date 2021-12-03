package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.olx.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer>,JpaSpecificationExecutor<PostEntity> {

	List<PostEntity> findByTitle(String title);

	List<PostEntity> findByUsername(String authToken);


	PostEntity findByIdAndUsername(int adId, String authToken);

	

	void deleteByIdAndUsername(@Param("id") int adId, @Param("username") String authToken);



	List<PostEntity> findByTitleOrDescriptionContainingIgnoreCase(@Param("title") String searchTextTitle, @Param("description") String searchTextDesc);
}
