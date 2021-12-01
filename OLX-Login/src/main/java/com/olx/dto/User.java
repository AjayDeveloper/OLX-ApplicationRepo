package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("User Application DTO holding basic info Login info")
public class User {
	    @ApiModelProperty(value ="Unique identifier for the Login")
	    private int id;
	    @ApiModelProperty(value="Login email")
	    private String email;
	    @ApiModelProperty(value="Login username")
	    private String username;
	    @ApiModelProperty(value="Login firstName")
	    private String firstName;
	    @ApiModelProperty(value="Login lastName")
	    private String lastName;
	    @ApiModelProperty(value="Login password")
	    private String password;
	    @ApiModelProperty(value="Login roles")
	    private String roles;
	  
	    

}
