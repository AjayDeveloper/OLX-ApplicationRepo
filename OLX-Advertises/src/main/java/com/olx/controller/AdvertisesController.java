package com.olx.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.olx.dto.Advertises;
import com.olx.exception.InvalidPostIdException;
import com.olx.service.MasterDataDelegate;
import com.olx.service.PostService;
import com.olx.util.DateUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/olx/advertise")
public class AdvertisesController {

	@Autowired
	PostService serivce;

	@Autowired
	MasterDataDelegate masterDataDelegate;
	
	private static final Logger logger = LoggerFactory.getLogger(AdvertisesController.class);

	@ExceptionHandler(value = { InvalidPostIdException.class })
	public ResponseEntity<Object> handleInvalidPostIdException(RuntimeException exception, WebRequest request) {
		return new ResponseEntity<Object>("Local Exception handler - invalid Post id", HttpStatus.BAD_REQUEST);
	}

	// baseUrlChange
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Advertises> createNewPost(@RequestHeader("auth-token") String authToken,
			@RequestBody Advertises advertises) {
		System.out.println("AUTH-TOKEN - " + authToken);
		String statusText = masterDataDelegate.getStatusById(advertises.getStatusId());
		advertises.setStatus(statusText);
		advertises.setStatusId(advertises.getStatusId());
		String categoryText = masterDataDelegate.getCategoryById(advertises.getCategoryId());
		advertises.setCategory(categoryText);
		advertises.setCategoryId(advertises.getCategoryId());

		return new ResponseEntity(serivce.createNewPost(authToken, advertises), HttpStatus.CREATED);
	}

	// baseUrlChange
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Advertises> updatePost(@PathVariable("id") int Id, @RequestBody Advertises advertises,
			@RequestHeader("auth-token") String authToken) {
		System.out.println("AUTH-TOKEN - " + authToken);

		return new ResponseEntity(serivce.updatePost(authToken, Id, advertises), HttpStatus.CONFLICT);
	}

	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Advertises>> getAllAdvertisementsPostByUser(
			@RequestHeader("auth-token") String authoToken) {
		System.out.println("AUTH-TOKEN - " + authoToken);
		return new ResponseEntity(serivce.getAllAdvertisementsPostByUser(authoToken), HttpStatus.OK);

	}

	@GetMapping(value = "/user/advertise/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Advertises> getAllAdvertisementsPostByUserId(@PathVariable("id") int id,
			@RequestHeader("auth-token") String authToken) {

		System.out.println("AUTH-TOKEN - " + authToken);
		return new ResponseEntity(serivce.getAllAdvertisementsPostByUserId(authToken, id), HttpStatus.OK);

	}

	@DeleteMapping(value = "/user/advertise/{id}")
	public ResponseEntity<Boolean> deleteAdvertisementByPostId(@PathVariable("id") int Id,
			@RequestHeader("auth-token") String authToken) {

		System.out.println("AUTH-TOKEN - " + authToken);
		return new ResponseEntity(serivce.deleteAdvertisementByPostId(authToken, Id), HttpStatus.OK);
	}

	// baseUrlChange
	@GetMapping(value = "/search/{name}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Advertises>> getAdvertisesByName(@PathVariable("name") String name) {
		return new ResponseEntity(serivce.findByTitle(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Search for an advertise filtered by different search criteria")
	@GetMapping(value = "/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Advertises>> searchAdvertisementBySearchCriteria(
			
			@RequestParam(value = "searchText",required =false) String searchText,
			@RequestParam(value = "category", defaultValue = "3", required = false) int categoryId,
			@RequestParam(value = "postedBy", required = false) String postedBy,
			@RequestParam(value = "dateCondition", required = false) String dateCondition,
			@RequestParam(value = "onDate", required = false) String onDate,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "sortBy", defaultValue = "asc", required = false) String sortBy,
			@RequestParam(value = "sortOn", defaultValue = "modifiedDate", required = false) String sortOn,
			@RequestParam(value = "startIndex", defaultValue = "0", required = false) int startIndex,
			@RequestParam(value = "records", defaultValue = "10", required = false) int records,
			@RequestParam(value = "status", defaultValue = "1", required = false) int statusId
			
			) {
		 
		  logger.info("ACCOUNT METHOD CALLED");
		return new ResponseEntity<List<Advertises>>(
				serivce.searchAdvertisementBySearchCriteria(searchText,
						categoryId,
						postedBy,
						dateCondition,
						onDate == null ? null : DateUtils.convertStringToDate(onDate, DateUtils.DATE_FORMAT_YYYY_MM_DD),
						fromDate == null ? null : DateUtils.convertStringToDate(fromDate, DateUtils.DATE_FORMAT_YYYY_MM_DD),
						toDate == null ? null : DateUtils.convertStringToDate(toDate, DateUtils.DATE_FORMAT_YYYY_MM_DD),
						sortBy,
						sortOn,
						startIndex,
						records,
						statusId),
				HttpStatus.OK);
	}

	
	
	
}
