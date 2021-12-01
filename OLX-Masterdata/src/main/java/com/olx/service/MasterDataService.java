package com.olx.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;

@Service
public interface MasterDataService {

	public List<Category> getAllcategory();

	public List<Status> getStatus();

	public String getStatusById(int statusId);

	public String getCategoryById(int categoryId);

}
