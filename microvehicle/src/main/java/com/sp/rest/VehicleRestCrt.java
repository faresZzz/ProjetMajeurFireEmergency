package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.dto.FireDto;
import com.sp.service.VehicleService;


@RestController
public class VehicleRestCrt {
	@Autowired
	VehicleService vService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.POST, value = "/newMission/{idVehicle}") // utilisation de la method post afin de pouvoir recupere une info avec restTemplate
	public boolean newMission(@PathVariable String idVehicle, @RequestBody FireDto fire) {
		System.out.println("NEW MISION");
		return vService.newMission(Integer.valueOf(idVehicle), fire);
	}
	

	
	
}
