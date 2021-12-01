package com.olx.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.olx.dto.Advertises;
import com.olx.exception.InvalidPostIdException;
import com.olx.service.MasterDataDelegate;
import com.olx.service.PostService;

@RestController
@RequestMapping(value = "/olx/advertise")
public class AdvertisesController {

	@Autowired
	PostService serivce;

	@Autowired
	MasterDataDelegate masterDataDelegate;

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
		return new ResponseEntity(serivce.getAllAdvertisementsPostByUserId(authToken,id), HttpStatus.OK);

	}

	@DeleteMapping(value = "/user/advertise/{id}")
	public ResponseEntity<Boolean> deleteAdvertisementByPostId(@PathVariable("id") int Id,
			@RequestHeader("auth-token") String authToken) {

		System.out.println("AUTH-TOKEN - " + authToken);
		return new ResponseEntity(serivce.deleteAdvertisementByPostId(authToken,Id), HttpStatus.OK);
	}

	// baseUrlChange
	@GetMapping(value = "/search/{name}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Advertises>> getAdvertisesByName(@PathVariable("name") String name) {
		return new ResponseEntity(serivce.findByTitle(name), HttpStatus.OK);
	}

}
