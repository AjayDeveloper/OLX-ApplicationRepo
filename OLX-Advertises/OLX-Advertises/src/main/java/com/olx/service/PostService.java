package com.olx.service;


import java.time.LocalDate;
import java.util.List;

import com.olx.dto.Advertises;



public interface PostService {
	
	public Advertises createNewPost(String authToken,Advertises advertises);
	public Advertises updatePost(String authToken,int id,Advertises advertises);
	public List<Advertises> getAllAdvertisementsPostByUser(String authToken);
	public Advertises getAllAdvertisementsPostByUserId(String authToken,int id);
	public boolean deleteAdvertisementByPostId(String authToken,int id);
	public List<Advertises> searchAdvertisementBySearchCriteria(String searchText, int categoryId, String postedBy, String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, String sortOn, int startIndex, int records, int statusId);
	
	List<Advertises> findByTitle(String title);
	
}
