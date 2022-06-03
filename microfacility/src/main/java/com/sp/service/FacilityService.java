package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.VehicleDto;
import com.sp.model.Facility;
import com.sp.repository.FacilityRepository;
// 164 151

@Service
public class FacilityService {
	
	@Autowired
	FacilityRepository fRepository;
	
	private static RestTemplate rest_template = new RestTemplate();
	private String URL_GET_VEHICLE = "http://vps.cpe-sn.fr:8081/vehicle";
	private String URL_PUT_VEHICULE =  "http://vps.cpe-sn.fr:8081/vehicle/0eb29fc1-d666-4dd6-9a6e-933f29f87689/";
	private Collection<Integer> list_id = new LinkedHashSet<Integer>();
	private List<Integer> listFacilityRefID = new ArrayList<Integer>();
	
	private List<VehicleDto> listVehicle;
	/*
	 * Methodesinterface
	 * */
	
	/**
	 *  Permet d'initialiser les vehicules
	 * */
	public void initVehicle() {
		// TODO : Chnager la methode quand on pourra obtenir la liste automatiquement
		this.listFacilityRefID.add(249);
	
		
		this.updateFacilities();
	}
	
	/**
	 * Permet de mettre a jour la db de vehicules
	 * */
	public void updateFacilities() {
		
//		/* Permet de recuperer tout les facility*/
//		HashMap<Integer,Facility> hash_map = this.getHashMap((ArrayList<Facility>)getHTTPFacilities());
//		
//		/* Pour les id de camions dans la liste de notre equipe*/
//		for (Integer id:this.list_id) {
//			/* Si le hashmap a un camion qui contient cet id, le sauvegarder*/
//			if(hash_map.containsKey(id.hashCode())) {
//				fRepository.save(hash_map.get(id.hashCode()));
//			}
//		}
	}
	
	/**
	 * show database
	 * */
	public String printFacilities() {
		return fRepository.findAll().toString();
	}
	
    
	
	public Collection<Facility> getHTTPVehicle() {
		
//		/**
//		 * Permet d'appeller via un get http une liste en json convertie en liste d'objet java
//		 * */
//		VehicleDTO[] Vehicle = rest_template.getForObject(URL_GET_VEHICLE, VehicleDTO[].class);
//	
//		ArrayList<Facility> fList = responseEntity.getBody();
//		for (Facility facility : fList) {
//			
//		}
//		
//		return fList;
		return null;
	}
	
	/**
	 * Permet de convertir un array en hashmap en utilisant
	 * */
	public HashMap<Integer,Facility> getHashMap(ArrayList<Facility> facilities){
		HashMap<Integer,Facility> ret = new HashMap<Integer,Facility>();
		
		for (Facility f : facilities) {
			ret.put(f.hashCode(), f);
		}
		
		return ret;
	}

}
