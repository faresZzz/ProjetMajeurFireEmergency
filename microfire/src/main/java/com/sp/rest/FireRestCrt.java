package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sp.service.FireService;


@RestController
public class FireRestCrt {
	@Autowired
	FireService fService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	public void tryFire() {
		System.out.println(fService.getAlldBFires());
	}
	
	
}
