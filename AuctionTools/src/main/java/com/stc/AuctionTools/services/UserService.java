package com.stc.AuctionTools.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stc.echannels.esdp.client.api.EsdpClientService;
import com.stc.echannels.esdp.client.api.exception.EsdpException;

@Service
public class UserService {

	final static String resourceId = "deleteLdapUser";
	
	@Autowired
	EsdpClientService esdpRestClient;
	
	public String deleteUserById(String userId)
	{
		//TODO implementation of delete user
		
		try {
			Map<String, String> urlVariables = new HashMap<>();
			urlVariables.put("USERNAME", userId);
			esdpRestClient.execute(resourceId, null,urlVariables);
		} catch (EsdpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
		return "success";
	}
}
