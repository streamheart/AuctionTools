package com.stc.test.AuctionTools;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.stc.AuctionTools.AuctionToolsApplication;
import com.stc.AuctionTools.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={AuctionToolsApplication.class})
public class AuctionToolsApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	 private MockRestServiceServer mockServer;
	 
	 @Autowired
	    private UserService service;
	 
	 @Before
	    public void setUp() {
	        mockServer = MockRestServiceServer.createServer(service.getRestTemplate());
	    }
	 
	 @Test
	    public void testAddReturnCorrectResponse() throws Exception {
		 mockServer.expect(requestTo(service.getUrl()+service.getResourcepath().replace("{USERNAME}", "umair777e")))
         .andExpect(method(HttpMethod.DELETE))
         .andRespond(withSuccess("success", MediaType.TEXT_PLAIN)); 
		 final String response = (String) service.deleteUserByUsernameAndPassword("umair777e", "s12345678");
		 assertEquals("success", response);
		 
		 mockServer.verify();
		 
		 
	 }

}
