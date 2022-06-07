package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.Coord;
import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.sp.model.Vehicle;
import com.sp.repository.VehicleRepository;
import com.sp.tools.VehicleTools;
// 164 151

@Service
public class VehicleService {
	
	private Thread vehicleTread;
	private VehicleRunnable vehicleRunnable;
	private VehicleDto vehicle;
	private FacilityDto facility;
	

	public boolean newMission(Integer idVehicle, FireDto fire) {
		// TODO  potentiellement tester si le vehicle est disponible , tester la quantitié de liquide et de carburant 
		
		this.vehicle = VehicleTools.getVehicle(idVehicle); // on recupere le vehicle
		this.facility = VehicleTools.getFacility(vehicle.getFacilityRefID()); // on recupere la facility associer
		
		this.vehicleRunnable = new VehicleRunnable(vehicle, fire, new Coord(this.facility.getLon(), this.facility.getLat()), 3); // on creé le runnable
		
		this.vehicleTread = new Thread(this.vehicleRunnable);// on creé le Thread 
		this.vehicleTread.start();// on lance le Tread
		
		return true;
	}

}
