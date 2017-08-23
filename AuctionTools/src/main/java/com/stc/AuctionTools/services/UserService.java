package com.stc.AuctionTools.services;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

	private final static String resourcePath = "/private/users/{USERNAME}/ldapuser";
	//change for branch testing
	
	@Value(value="${stc.rest.client.user-agent}")
	private String useragent;
	@Value(value="${stc.rest.client.language}")
	private String language;
	
	
	private RestTemplate restTemplate = new RestTemplate();
	@Value(value = "${stc.rest.client.base-uri}")
	private String url;
	
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

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static String getResourcepath() {
		return resourcePath;
	}
	
	
}
