package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MasterDataDelegateImpl implements MasterDataDelegate {

	@Autowired
	RestTemplate restTemplate;
	
	
	@Override
	@CircuitBreaker(name = "STATUS-FROM-MASTER-DATA-SERVICE", fallbackMethod = "fallback")
	public String getStatusById(int statusId) {
		ResponseEntity<String> entityStatusText = restTemplate
					.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/status/" + statusId, String.class);
			System.out.println("Advertises MasterDataDelegate" + entityStatusText);
			return entityStatusText.getBody();

	}

	@Override
	public String getCategoryById(int categoryId) {
		
			ResponseEntity<String> entityResponseText = restTemplate
					.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/category/" + categoryId, String.class);
			System.out.println("Advertises MasterDataDelegate Category" + entityResponseText);
			return entityResponseText.getBody();

		
	}

	
	 public String fallback(int statusId, Exception e) {
	  
	  System.out.println("Service Issue" + e); return null; }
	 

}
