package com.sp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sp.service.FacilityService;


@RestController
public class FacilityRestCrt {
	@Autowired
	FacilityService fService;
//	
//	
//	@RequestMapping(method = RequestMethod.POST, value = "/feu")
//	public void gererFeu(@RequestBody FeuDTO feu) {
//		fService.initFacility();
//		System.out.println(fService.printFacilities()) ;
//	}
//	 @RequestMapping(method=RequestMethod.POST,value="/Card")
//     public void addCard(@RequestBody Card Card) {
//         cService.addCard(Card);
//     }
//	
	
}
