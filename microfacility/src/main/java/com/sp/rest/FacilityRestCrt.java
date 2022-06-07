package com.sp.rest;

import java.io.Console;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.sp.service.FacilityService;


@RestController
public class FacilityRestCrt {
	@Autowired
	FacilityService fService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/handleFire/{idCaserne}")
	public boolean handleFire(@RequestBody FireDto fire, @PathVariable String idCaserne) {
		System.out.println(fire);
		System.out.println(idCaserne);
		return fService.envoyerVehicleEnIntervention(Integer.valueOf(idCaserne), fire);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/endFire/{idFire}")
	public void endFire(@PathVariable String idFire) {
		System.out.println(idFire);
		fService.endFire(Integer.valueOf( idFire));
	}
	
	
	
	
}
