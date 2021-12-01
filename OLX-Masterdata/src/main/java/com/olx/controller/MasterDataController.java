package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.service.MasterDataService;

@RestController
@RequestMapping(value="/olx/masterdata")
public class MasterDataController {

	@Autowired
	private MasterDataService masterDataService;
	
	
	
	@GetMapping(value="/advertise/category",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Category>>getAllcategory(){
		
		return new ResponseEntity(this.masterDataService.getAllcategory(),HttpStatus.OK);
	}
	
	@GetMapping(value="/advertise/status",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public  ResponseEntity<List<Status>> getStatus(){
		
		return new ResponseEntity(this.masterDataService.getStatus(),HttpStatus.OK);
	}
	@GetMapping(value="/advertise/status/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> getStatusById(@PathVariable("id") int statusId){
		// System.out.println("MasterDataController"+this.masterDataService.getStatusById(statusId));
		return new ResponseEntity(this.masterDataService.getStatusById(statusId),HttpStatus.OK);
	}
	
	@GetMapping(value="/advertise/category/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> getCategoryById(@PathVariable("id") int categoryId){
		 //System.out.println("MasterDataController"+this.masterDataService.getCategoryById(categoryId));
		return new ResponseEntity(this.masterDataService.getCategoryById(categoryId),HttpStatus.OK);
	}
	 
	
}
