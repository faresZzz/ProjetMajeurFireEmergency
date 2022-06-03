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
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/gererFire/{idCaserne}")
	public boolean gererFeu(@RequestBody FireDto Fire, @PathVariable String idCaserne) {
		List<VehicleDto> listVehicle  =  fService.getHTTPVehicle(idCaserne);
		return fService.envoyerCamion(listVehicle,Fire);
		
	}
	
	
	
}
