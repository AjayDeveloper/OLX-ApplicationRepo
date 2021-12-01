package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.exception.InvalidCategoryIdException;
import com.olx.exception.InvalidStatusIdException;
import com.olx.repository.CategoryRepository;
import com.olx.repository.StatusRepository;

@Service
public class MasterDataServiceImp implements MasterDataService {

	@Autowired
	private CategoryRepository masterDataRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	
	@Autowired 
	private CategoryRepository categoryRepository;
	 
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<Category> getAllcategory() {
		List<CategoryEntity> categoryEntities = masterDataRepository.findAll();
		List<Category> categoriesDtoList = new ArrayList<Category>();
		TypeMap<CategoryEntity,Category> typeMap = this.modelMapper.typeMap(CategoryEntity.class, Category.class);
		typeMap.addMappings((mapper) -> {
		      	mapper.map(source->source.getDesc(),Category::setDescription);
		      	
		});
		for(CategoryEntity categoryEntity : categoryEntities) {
			
			Category categoryDto = this.modelMapper.map(categoryEntity,Category.class);
			categoriesDtoList.add(categoryDto);
			
		}
		
		return categoriesDtoList;
	}

	@Override
	public List<Status> getStatus() {
		List<StatusEntity> statusEntities = statusRepository.findAll();
		List<Status> statusDtoList = new ArrayList<Status>();
		for(StatusEntity statusEntity : statusEntities) {
			Status statusDto = new Status();
			statusDto.setId(statusEntity.getId());
			statusDto.setStatus(statusEntity.getStatus());
			statusDtoList.add(statusDto);
		     
		}
		return statusDtoList;
	}

	@Override
	public String getStatusById(int statusId) {
		Optional<StatusEntity> statusEntities = statusRepository.findById(statusId);
		if(statusEntities.isPresent()) {
			String status = statusEntities.get().getStatus();
	        System.out.println("MasterDataImple service override method" + status);
			return status;
		}
		
	   throw new InvalidStatusIdException("" + statusId);
	}
   
	//currently change
	@Override
	public String getCategoryById(int categoryId) {
		Optional<CategoryEntity> categoryEntities = categoryRepository.findById(categoryId);
		if (categoryEntities.isPresent()) {
			String category = categoryEntities.get().getCategory();
			System.out.println("MasterDataImple service override method" + category);
			return category;
		}
		throw new InvalidCategoryIdException(""+categoryId);
		
	}
	
	

	
	
}
