package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TowerRestCrt {
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value = "/move")
	public void move() {
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	public void start() {
	}
	
	
}
