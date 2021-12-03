package com.olx.entity;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(value="backlist")
public class UserDocument {
    @Indexed(direction=IndexDirection.ASCENDING)
	private String token;
	
	
}
