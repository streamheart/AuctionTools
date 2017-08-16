package com.stc.AuctionTools.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stc.AuctionTools.services.UserService;

@RestController
public class UserController {

	@Autowired
	public UserService userService;
	
	
	@RequestMapping(value="/user/deleteuser/{userId}", method=RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
	public String deleteUser(@PathVariable("userId") String userId)
	{
		String result = userService.deleteUserById(userId);
		if("success".equals(result))
		{
			return "successfully deleted user with id " + userId;
		}
		else
		{
			return "error encountered while deleting user";
		}
	}
}
