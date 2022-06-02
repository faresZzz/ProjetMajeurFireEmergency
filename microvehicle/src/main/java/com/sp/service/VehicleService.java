package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sp.model.Vehicle;
import com.sp.repository.VehicleRepository;
// 164 151

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vRepository;
	
	private static RestTemplate rest_template = new RestTemplate();
	private String URL_GET_VEHICLE = "http://vps.cpe-sn.fr:8081/vehicle";
	private String
	
	private Collection<Integer> list_id = new LinkedHashSet<Integer>();
	
	/*
	 * Methodes
	 * */
	
	public void initVehicles() {
		// TODO : Chnager la methode quand on pourra obtenir la liste automatiquement
		this.list_id.add(164);
		this.list_id.add(151);
		
		HashMap<Integer,Vehicle> hash_map = this.getHashMap((ArrayList)this.getHTTPVehicles());
		
		for (Integer id:this.list_id) {
			
			if(hash_map.containsKey(id.hashCode())) {
				vRepository.save(hash_map.get(id.hashCode()));
			}
		}
	}
	
	
	/**
	 * Permet de deplacer un vehicule d'un point A à un point B
	 * */
	public boolean moveVehicle(Integer id, Double lon, Double lat) {
		Vehicle v = vRepository.findById(id).get();
		
		v.setLon(lon);
		v.setLat(lat);
		
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Vehicle> request = new HttpEntity<Vehicle>(v));
		restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);
		
		return true;
	}
    
	/**
	 * Saves the fire to the database
	 * */
	private void addVehicle(Vehicle v) {
		vRepository.save(v); // Sauvegarde du user dans la db	
	}
	
	private void addAllVehicle(Collection<Vehicle> vlist) {
		vRepository.saveAll(vlist);
	}
	
	
	public Iterable<Vehicle> getAlldBFires() {
		Iterable<Vehicle> vOpt = vRepository.findAll();
		return vOpt;
	}
	
	public Collection<Vehicle> getHTTPVehicles() {
		
		/**
		 * Permet d'appeller via un get http une liste en json convertie en liste d'objet java
		 * */
		ResponseEntity<ArrayList<Vehicle>> responseEntity = 
				  rest_template.exchange(
					URL_GET_VEHICLE,
				    HttpMethod.GET,
				    null,
				    new ParameterizedTypeReference<ArrayList<Vehicle>>() {}
				  );
		
		ArrayList<Vehicle> vList = responseEntity.getBody();
		
		
		return vList;
	}
	
	/**
	 * Permet de convertir un array en hashmap en utilisant
	 * */
	public HashMap<Integer,Vehicle> getHashMap(ArrayList<Vehicle>vehicles){
		HashMap<Integer,Vehicle> ret = new HashMap<Integer,Vehicle>();
		
		for (Vehicle vehicle : vehicles) {
			ret.put(vehicle.hashCode(), vehicle);
		}
		
		return ret;
		
	}

}
