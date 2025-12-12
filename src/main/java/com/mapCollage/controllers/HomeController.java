package com.mapCollage.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	@GetMapping("/home")
    String printHello() {
    	System.out.print("Hello World From printHello Function");
    	return "Hello";
    }
}
