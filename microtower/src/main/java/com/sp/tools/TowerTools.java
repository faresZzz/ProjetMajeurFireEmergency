package com.sp.tools;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;

public class TowerTools {
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	// URLs
	private static String URL_DB = "http://vps.cpe-sn.fr:8081/";
	private static String URL_FACILITY = "http://localhost:8083/";
	
	private static String URL_PUT_FIRE_FACILITY = URL_FACILITY + "handleFire/";
	private static String URL_END_FIRE_FACILITY = URL_FACILITY + "endedFire/";
	private static String URL_GET_FACILITIES = URL_DB + "facility";
	
	
	public static FacilityDto[] getFacilities() {
		return restTemplate.getForObject(URL_GET_FACILITIES,  FacilityDto[].class) ;
	}


	public static boolean endFire(Integer id) {
		
		return restTemplate.getForObject(URL_END_FIRE_FACILITY + id,  Boolean.class);
	}


	public static boolean sendFire(int idCaserne, FireDto fire) { // on utilise pas fa ????
		
		return restTemplate.postForObject(URL_PUT_FIRE_FACILITY + idCaserne, fire, Boolean.class);
//		HttpEntity<FireDto> request = new HttpEntity<FireDto>(fire);
//		restTemplate.exchange(
//				URL_PUT_FIRE_FACILITY+fire.getId().toString(), 
//				HttpMethod.POST, 
//				request, 
//				Boolean.class);
	}

}
