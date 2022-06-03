package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sp.model.Fire;
import com.sp.service.FireService;


@RestController
public class FireRestCrt {
	@Autowired
	FireService fService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value = "/fires")
	public Fire[] getFire() {
		return fService.getAlldBFires();
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/fire/{idFire}")
	public Fire getFire(@PathVariable String idFire) {
		return fService.getFireById( Integer.valueOf(idFire));
	}
	
	
}
