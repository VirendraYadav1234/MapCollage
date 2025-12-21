package com.mapCollage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class HomeController {
	@GetMapping("/home")
    String printHello(HttpServletRequest request) {
    	System.out.print("Hello World From printHello Function");
		return request.getSession().getId();
    }
	
	@GetMapping("/session")
	String sessionId(HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("name"));
		return request.getSession().getId();
	}
	@GetMapping("/password")
	public String sessionInfo(HttpServletRequest request) {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String username = authentication.getName(); // logged-in user

	    System.out.println("Username: " + username);
	    System.out.println("Session ID: " + request.getSession().getId());

	    return username;
	}
	
	

}
