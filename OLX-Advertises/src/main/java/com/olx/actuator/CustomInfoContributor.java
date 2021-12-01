package com.olx.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.olx.repository.PostRepository;
import com.olx.service.MasterDataDelegate;


@Component
public class CustomInfoContributor implements InfoContributor {


	@Autowired
	PostRepository postRepository;
	@Autowired
	MasterDataDelegate masterDataDelegate;
	
	@Override
	public void contribute(Builder builder) {
		
		builder.withDetail("NO Of Advertiese", postRepository.count());
		//builder.withDetail("status_of_close", );
		
	}

}
