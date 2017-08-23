package com.stc.AuctionTools.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.net.MediaType;
import com.stc.AuctionTools.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value="Auction Tools", description="Operations pertaining to auction users")
public class UserController {

	@Autowired
	public UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/user/deleteuser", method=RequestMethod.DELETE, produces="text/plain")
	@ApiOperation(value = "Delete a user from LDAP", response = String.class)
	@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted user"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource, or user already deleted"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public String deleteUser(@RequestParam("username") String username, @RequestParam("password") String password)
	{
		
		String result = userService.deleteUserByUsernameAndPassword(username, password);
		
		return result;
	}
	
	@ApiOperation(value = "Show delete user html page", response = String.class)
	@RequestMapping(value="/user/deleteuser",method=RequestMethod.GET)
	public String showDeleteUser(Model model)
	{
		return "deleteuser";
	}
}
