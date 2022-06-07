package com.sp.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.quantity.Length;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.sp.tools.TowerTools;

// 164 151

/*
 *  TODO envoi a la facility secondaire si la premiere n'as pas de camion dispo
 *  Ajout selection des feu que l'on decide de prendre (fonction de la distance a la facility (type de feu ))
 *  Peut etre selection de la facility en fonction des types de camions qu'elle a et du type de feu
 *  
 */
@Service
public class TowerService {
	 

	
	private Map<Integer,FireDto> fire_map; // Id avec le feu associé
	private Map<Integer,FacilityDto> facility_map; // Id avec la facility associée
	
	private Map<Integer,Set<Integer>> fireInRangeFacility; // Lie les facilities et les feux qui sont dans le range  
	private Map<Integer,Integer> facilityInActionFire; // lie les feu et les facilities qui sont entraint de s'en occuper
	
	

	/*
	 * Methodes
	 * */
	
	
	/**
	 * Initialisation de la tour
	 * La tour ne sait pas quel camion gere le feu mais connait la facility
	 * */
	public void initTower(FireDto[] fires) {
		this.facilityInActionFire = new HashMap<Integer,Integer>();
		 this.getFacilities();
		 this.fire_map = new HashMap<Integer,FireDto>();
		 
		 for (FireDto fi : fires) {
			this.fire_map.put(fi.getId(), fi);
		}
		
		 this.determineFireForAllFacilities(); // Feu les plus proche des facility
	}
	
	/**
	 * Permet de recuperer les facility
	 * @return 
	 * */
	public void getFacilities() {
		facility_map = new HashMap<Integer,FacilityDto>();
		/* Fais la requete pour obtenir une liste de casernes*/
		FacilityDto[] resp = TowerTools.getFacilities();
		
		/* Change cet objet Array en Liste */
		for (FacilityDto facilityDto : Arrays.asList(resp)) 
		{
			facility_map.put(facilityDto.getId(),facilityDto);
		} 
		
	}
	
	/**
	 * Envoi le feu le plus proche de chaque facility
	 * */
	public void determineFireForAllFacilities(){
		for (FacilityDto fa : facility_map.values()) {
			FireDto fi = this.getClosestFire(fa);
			this.facilityInActionFire.put(fi.getId(), fa.getId());
			this.sendFire(fi, fa);
			
		}
	}
	
	public void determineFireForOneFacility(Integer facility_id) {
		FacilityDto fa = this.facility_map.get(facility_id);
		FireDto fi = this.getClosestFire(fa);
		this.facilityInActionFire.put(fi.getId(), fa.getId());
		this.sendFire(fi, fa);
	}
	
	/**
	 * Cas ou un feu est finit, permet de notifier le
	 * */
	
	public void recieveEndedFire(FireDto f) {
		this.fire_map.remove(f.getId());
		this.determineFireForOneFacility(this.facilityInActionFire.get(f.getId()));
		this.facilityInActionFire.remove(f.getId());
		
	}
	
	/**
	 * Permet d'ajouter un nouveau feu a la caserne
	 * */
	public void recieveNewFire(FireDto fi) {
		fire_map.put(fi.getId(), fi);
		
		/* Verifie que la liste des casernes est bien initialisée*/
		//if(this.facility_map==null) { this.getFacilities(); }
		
		
		/* Récupere la caserne la plus proche */
		//FacilityDto fa = this.getClosestFacility(fi);
		
		/* Envoyer le feu a lafacilité la plus proche*/
		//this.sendFire(fi, fa);
		
		//managed_fire_set.put(fi.getId(), fa.getId());
	}
	
	/**
	 * Distance entre un feu et une facility
	 * */
	private Float calculateDistance(FacilityDto fa, FireDto fi) {
		return (float) Math.sqrt( Math.pow(fa.getLat()-fi.getLat(), 2) + Math.pow(fa.getLon()-fi.getLon(), 2) );
	}
	
	
	/**
	 * Calculates the closest facility to the fire
	 * */
	private FacilityDto getClosestFacility(FireDto fi) {
		float min_dist = 1000000000000f;
		FacilityDto ret = null; 
		
		for (FacilityDto fa : facility_map.values()) {
			float dist = this.calculateDistance(fa, fi); // Calcule la distance min
			
			if(dist < min_dist) {
				min_dist = dist;
				ret = fa;
			}
		}
		return ret;
	}
	
	/**
	 * Prendle feu le plus proche de la facility
	 * */
	private FireDto getClosestFire(FacilityDto fa) {
		
		float min_dist = 1000000000000f;
		FireDto ret = null; 
		
		for (FireDto fi : fire_map.values()) {
			float dist = this.calculateDistance(fa, fi); // Calcule la distance min
			
			if(dist < min_dist) {
				min_dist = dist;
				ret = fi;
			}
		}
		return ret;
	}
	
	
	
	/**
	 * Send fire
	 * */
	public Boolean sendFire(FireDto fi, FacilityDto fa) { 
		Boolean ret = false;
		try {
	
			ret = TowerTools.sendFire(fa.getId(), fi);
			
		}
		catch( HttpClientErrorException httpClientErrorException) {
			System.out.println(httpClientErrorException.getResponseBodyAsString()); 
		}
		return ret;
	}
}
	
