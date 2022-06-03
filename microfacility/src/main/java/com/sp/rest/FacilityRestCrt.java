package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sp.service.FacilityService;
import com.sp.service.VehicleService;


@RestController
public class FacilityRestCrt {
	@Autowired
	FacilityService fService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value = "/move")
	public void move() {
		fService.moveFacility(250, 50.0, 50.0);
		System.out.println(fService.printFacilities());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	public void start() {
		fService.initFacility();
		System.out.println(fService.printFacilities()) ;
	}
	
	
}
