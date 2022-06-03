package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.sp.model.dto.FacilityDto;
import com.sp.model.dto.FireDto;
// 164 151

@Service
public class TowerService {
	
	@Autowired
	private static RestTemplate rest_template = new RestTemplate();
	Set<FireDto> fire_set;
	Set<FacilityDto> facility_set;
	
	// URLs
	private String URL_PUT_VEHICULE = "localhost:8082/manageFire/";
	private String URL_GET_FACILITIES = "localhost:8081/getFacilities";

	/*
	 * Methodes
	 * */
	
	/**
	 * Permet de recuperer les facility
	 * */
	public void getFacilities() {
		rest_template.getForObject(URL_GET_FACILITIES,  FacilityDTO.class);
	}
	
	/**
	 * Permet d'envoyer un nouveau feu a la caserne voulu
	 * */
	public void recieveNewFire(FireDTO fi) {
		FacilityDto fa = this.getClosestFacility(fi);
		this.sendFire(fi, fa);
	}
	
	/**
	 * Distance entre un feu et une facility
	 * */
	private Float calculateDistance(FacilityDto fa, FireDto fi) {
		return (float) Math.sqrt( Math.pow(fa.getLat()-fi.getLat(), 2) - Math.pow(fa.getLon()-fi.getLon(), 2));
	}
	
	/**
	 * Calculates the closest facility to the fire
	 * */
	private FacilityDto getClosestFacility(FireDto fi) {
		float min_dist = 1000000000000f;
		FacilityDto ret = null;
		for (FacilityDto fa : facility_set) {
			float dist = this.calculateDistance(fa, fi);
			if(dist < min_dist) {
				min_dist = dist;
				ret = fa;
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
			
			HttpEntity<FireDto> request = new HttpEntity<FireDto>(fi);
			rest_template.exchange(
					this.URL_PUT_VEHICULE+fi.getId().toString(), 
					HttpMethod.POST, 
					request, 
					Boolean.class);
			ret = true;
		}
		catch( HttpClientErrorException httpClientErrorException) {
			System.out.println(httpClientErrorException.getResponseBodyAsString()); 
		}
		return ret;
	}

}
