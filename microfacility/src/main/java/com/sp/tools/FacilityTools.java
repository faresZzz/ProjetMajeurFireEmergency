package com.sp.tools;

import org.springframework.web.client.RestTemplate;

import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class FacilityTools {
	
	private static RestTemplate restTemplate = new RestTemplate();

	private static final String URlCPE_SERVICE = "http://vps.cpe-sn.fr:8081/";
	private static final String URLVEHICLE_SERVICE = "http://localhost:8081/";
	private static final String URLTOWER_SERVICE = "http://localhost:8082/";
	private static final String UUID = "0eb29fc1-d666-4dd6-9a6e-933f29f87689";


	public static FacilityDto getFacility(Integer facilityId) {

		return restTemplate.getForObject(URlCPE_SERVICE + "/facility/" + facilityId, FacilityDto.class);
	}


	public static VehicleDto[] getVehicle() {

		return restTemplate.getForObject(URlCPE_SERVICE + "/vehicle", VehicleDto[].class);
	}

	
	public static VehicleDto getVeHicleById(int vehicleId)
	{
		return restTemplate.getForObject(URlCPE_SERVICE + "/vehicle/" + vehicleId, VehicleDto.class);
	}

	public static boolean envoyerVehicleEnMission(int vehicleId, FireDto fire) {
		return restTemplate.postForObject(URLVEHICLE_SERVICE + "/newMission/" + vehicleId, fire, Boolean.class);

	}


	public static void updateControlTower(int fireId) {
		
		restTemplate.put( URLTOWER_SERVICE + "/endFire/" + fireId, "nada",String.class);

		
	}


}
