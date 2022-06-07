package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.dto.FireDto;
import com.sp.service.TowerService;


@RestController
public class TowerRestCrt {
	@Autowired
	TowerService towerService;
	
	/**
	 * Feu finit
	 * */
	@RequestMapping(method = RequestMethod.POST, value = "/fireStarted")
	public void fireStarted(@RequestBody FireDto f) {
		towerService.recieveNewFire(f);
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/endFire/{fireId}")
	public void endFire(@PathVariable String fireId) {
		towerService.recieveEndedFire(Integer.valueOf(fireId));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/initTower")
	public Boolean initTower(@RequestBody FireDto[] fires) {
		towerService.initTower(fires);
		return true;
	}
	
	
	
}
