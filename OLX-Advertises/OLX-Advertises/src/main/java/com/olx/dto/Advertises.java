package com.olx.dto;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Advertises Application DTO holding basic info Advertises info")
public class Advertises {
	@ApiModelProperty(value ="Unique identifier for the Advertises")
	private int id;
	@ApiModelProperty(value="Advertise's title")
	private String title;
	@ApiModelProperty(value="Advertise's price")
	private double price;
	@ApiModelProperty(value="Advertise's categoryId")
	private int categoryId;
	@ApiModelProperty(value="Advertise's statusId")
	private int statusId;
	@ApiModelProperty(value="Advertise's description")
	private String description;
	@ApiModelProperty(value="Advertise's createdDate")
	private LocalDate createdDate;
	@ApiModelProperty(value="Advertise's modifiedDate")
	private LocalDate modifiedDate;
    @ApiModelProperty(value="Advertise's status") 
    private String status;
    @ApiModelProperty(value="Advertise's category")
	private String category;
    @ApiModelProperty(value="Advertise's posted_by")
    private String posted_by;
    @ApiModelProperty(value = "Username of a user who has posted the advertise")
    private String username;

	
	 public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
	 
	
	
}
