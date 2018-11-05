package com.adv.enhance.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adv.enhance.engine.AdvReqEnhancementService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;



@RestController
@EnableAutoConfiguration
public class AdvReqEnhancementController {

	@Autowired
	AdvReqEnhancementService rSrv;
	
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping(value = "/api/enhance/site", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getEnhancedAdvReq(HttpServletRequest request, @RequestBody String requestBody) throws JsonParseException, JsonMappingException, IOException {
		
		String countryCode = null;
		// Check the ip address first, if any exception or non-US code, abort!
		try {
			countryCode = rSrv.getGeo(request.getRemoteAddr());
			if(!countryCode.equals("US"))
				return new ResponseEntity("Service not available!", HttpStatus.FORBIDDEN);
		} catch(Exception e) {
			return new ResponseEntity("Service not available!", HttpStatus.FORBIDDEN);
		}
		
		
		JsonNode site = rSrv.getEnhancedSite(requestBody);
		return new ResponseEntity(site, HttpStatus.OK);
	}
}
