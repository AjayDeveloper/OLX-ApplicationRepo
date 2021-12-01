package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("MasterData  DTO holding basic info Category info")
public class Category {
	  @ApiModelProperty(value ="Unique identifier for the Category")
      private int id;
	  @ApiModelProperty(value ="category name")
      private String category;
	  @ApiModelProperty(value ="category description")
      private String description;
      
      
}
