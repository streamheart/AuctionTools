package com.stc.AuctionTools.services;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

	final static String resourcePath = "/private/users/{USERNAME}/ldapuser";
	//change for branch testing
	
	@Value(value="${stc.rest.client.user-agent}")
	String useragent;
	@Value(value="${stc.rest.client.language}")
	String language;
	
	RestTemplate restTemplate = new RestTemplate();
	@Value(value = "${stc.rest.client.base-uri}")
	String url;
	
	@Autowired
	UtilityServices utilityServices;
	
	public String deleteUserByUsernameAndPassword(String username, String password)
	{
			
			Locale locale = new Locale(language);
			
			MultiValueMap<String, String> headers = utilityServices.buildHeaders(locale, username, password, useragent, language);
			Map<String, String> urlVariables = new HashMap<>();
			urlVariables.put("USERNAME", username);
			
			try
			{
				HttpEntity<?> request = new HttpEntity<Object>(headers);
				restTemplate.exchange(url+resourcePath,HttpMethod.DELETE, request, String.class, urlVariables);
			}
			catch (Exception e) {
				System.out.println("error = " + e.getMessage());
				return e.getMessage();
			}
	
		
		
	
		return "success";
	}
}
