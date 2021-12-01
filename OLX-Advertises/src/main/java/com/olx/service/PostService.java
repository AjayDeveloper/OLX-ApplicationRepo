package com.olx.service;


import java.util.List;

import com.olx.dto.Advertises;



public interface PostService {
	
	public Advertises createNewPost(String authToken,Advertises advertises);
	public Advertises updatePost(String authToken,int id,Advertises advertises);
	public List<Advertises> getAllAdvertisementsPostByUser(String authToken);
	public Advertises getAllAdvertisementsPostByUserId(String authToken,int id);
	public boolean deleteAdvertisementByPostId(String authToken,int id);
	
	List<Advertises> findByTitle(String title);
	
}
