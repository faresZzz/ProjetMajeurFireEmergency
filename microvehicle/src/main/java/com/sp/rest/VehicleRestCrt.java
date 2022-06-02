package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sp.service.VehicleService;


@RestController
public class VehicleRestCrt {
	@Autowired
	VehicleService vService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value = "/move")
	public void move() {
		vService.moveVehicle(250, 0.0, 0.0);
		System.out.println(vService.printVehicules());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	public void start() {
		vService.initVehicules();
		System.out.println(vService.printVehicules()) ;
	}
	
	
}
