package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.sp.model.Facility;
import com.sp.repository.FacilityRepository;
// 164 151

import net.bytebuddy.asm.Advice.This;

@Service
public class FacilityService {
	
	@Autowired
	FacilityRepository fRepository;
	
	private static RestTemplate rest_template = new RestTemplate();
	
	private String URL_GET_VEHICLE = "http://vps.cpe-sn.fr:8081/vehicle";
	private String URL_POST_MOVE_VEHICLE = "http://localhost:8084/move";
	private String URL_PUT_VEHICULE =  "http://vps.cpe-sn.fr:8081/vehicle/0eb29fc1-d666-4dd6-9a6e-933f29f87689/";
	
	private Collection<Integer> list_id = new LinkedHashSet<Integer>();
	private List<Integer> listFacilityRefID = new ArrayList<Integer>(){{
		add(249);
	}};
	
	private List<VehicleDto> listVehicle ;
	
	private List<Integer> listVehicleEnInterventions ;
	
	
	/*
	 * Methodesinterface
	 * */
	
	/**
	 *  Permet d'initialiser les vehicules
	 * */
//	public List<VehicleDto> getVehicle() {
//		// TODO : Chnager la methode quand on pourra obtenir la liste automatiquement
//		this.listFacilityRefID.add(249);
//		return null;
//		
//		this.updateFacilities();
//		return listVehicle;
//	}
	
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
	
    
	public List<VehicleDto> getHTTPVehicle(String facilityRefID) {
		
		/**
		 * Permet d'appeller via un get http une liste en json convertie en liste d'objet java
		 * */
		
		Integer idCaserne = Integer.valueOf(facilityRefID);
		
		VehicleDto[] Vehicle = rest_template.getForObject(URL_GET_VEHICLE, VehicleDto[].class);
		for (VehicleDto vehicleDto : Vehicle) {
			if ((int)idCaserne == (int) vehicleDto.getFacilityRefID()) {			
				listVehicle.add(vehicleDto);			
			}
		}
		return listVehicle;
	}
	public boolean envoyerCamion(List<VehicleDto> listVehicle , FireDto Fire) {
		// TODO Auto-generated method stub
		boolean ret = false;
		for (VehicleDto vehicle : listVehicle) {
			if (!listVehicleEnInterventions.contains(vehicle.getId())) {
				listVehicleEnInterventions.add(vehicle.getId());
				rest_template.postForObject(URL_POST_MOVE_VEHICLE, Fire, null);
				ret = true; 
				break;
			}
		}
		return ret;
		
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
