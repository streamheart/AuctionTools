package com.stc.AuctionTools.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stc.AuctionTools.services.UserService;

@Controller
public class UserController {

	@Autowired
	public UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/user/deleteuser", method=RequestMethod.DELETE)
	public String deleteUser(@RequestParam("username") String username, @RequestParam("password") String password)
	{
		
		String result = userService.deleteUserByUsernameAndPassword(username, password);
		
		return result;
	}
	
	@RequestMapping(value="/user/deleteuser",method=RequestMethod.GET)
	public String showDeleteUser(Model model)
	{
		return "deleteuser";
	}
}
