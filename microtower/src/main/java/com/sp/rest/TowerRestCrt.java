package com.sp.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sp.model.dto.FireDto;
import com.sp.service.TowerService;

@RestController
public class TowerRestCrt {
	
	TowerService towerService;
	
	/**
	 * 
	 * */
	@RequestMapping(method = RequestMethod.POST, value = "/fireEnded")
	public void fireEnded(@RequestBody FireDto f) {
		towerService.recieveEndedFire(f);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fireStarted")
	public void fireStarted(@RequestBody FireDto f) {
		towerService.recieveNewFire(f);
	}
	
	
}
