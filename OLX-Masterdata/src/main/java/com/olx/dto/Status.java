package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("MasterData  DTO holding basic info Status info")
public class Status {
	@ApiModelProperty(value ="Unique identifier for the Status")
	private int id;
	@ApiModelProperty(value ="status Type")
	private String status;
	
}
