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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

	/*
	 * Methodes
	 * */
	
	/**
	 *  Permet d'initialiser les vehicules
	 * */
	public void initFacility() {
		// TODO : Chnager la methode quand on pourra obtenir la liste automatiquement
		this.list_id.add(250);
		this.list_id.add(151);
		
		this.updateFacilities();
	}
	
	/**
	 * Permet de mettre a jour la db de vehicules
	 * */
	public void updateFacilities() {
		
		/* Permet de recuperer tout les vehicules*/
		HashMap<Integer,Facility> hash_map = this.getHashMap((ArrayList<Facility>)this.getHTTPFacilities());
		
		/* Pour les id de camions dans la liste de notre equipe*/
		for (Integer id:this.list_id) {
			/* Si le hashmap a un camion qui contient cet id, le sauvegarder*/
			if(hash_map.containsKey(id.hashCode())) {
				fRepository.save(hash_map.get(id.hashCode()));
			}
		}
	}
	
	/**
	 * show database
	 * */
	public String printFacilities() {
		return fRepository.findAll().toString();
	}
	
    
	/**
	 * Saves the fire to the database
	 * */
	private void addFacility(Facility f) {
		fRepository.save(f); // Sauvegarde du user dans la db	
	}
	
	private void addAllFacility(Collection<Facility> vlist) {
		fRepository.saveAll(vlist);
	}
	
	
	public Iterable<Facility> getAlldBFires() {
		Iterable<Facility> vOpt = fRepository.findAll();
		return vOpt;
	}
	
	public Collection<Facility> getHTTPFacilities() {
		
		/**
		 * Permet d'appeller via un get http une liste en json convertie en liste d'objet java
		 * */
		ResponseEntity<ArrayList<Facility>> responseEntity = 
				  rest_template.exchange(
					URL_GET_VEHICLE,
				    HttpMethod.GET,
				    null,
				    new ParameterizedTypeReference<ArrayList<Facility>>() {}
				  );
		
		ArrayList<Facility> fList = responseEntity.getBody();
		
		
		return fList;
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
