package com.sp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.Coord;
import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class VehicleTools {
	
	private static final String URlCPE_SERVICE = "http://vps.cpe-sn.fr:8081/";
	private static final String MAPBOX_URL = "https://api.mapbox.com/directions/v5/mapbox/driving-traffic/";
	
	private static final String URLFACILIY_ENDFIRE = "http://localhost:8083/endFire/";
	
	private static final String UUID = "0eb29fc1-d666-4dd6-9a6e-933f29f87689";
	private static final String MAPBOX_TOKEN = "pk.eyJ1IjoiY2FwdGFpbjU1IiwiYSI6ImNsNDJveGVvNjAyejUzYm44aGYyMXY0dzIifQ.o1nuskLWQicA6hqPqMV0VA";
	
	
	public static void deplacementVehicle(VehicleDto vehicle) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(URlCPE_SERVICE + "vehicle/" + UUID + "/" + vehicle.getId(), vehicle);
		
	}

	public static FacilityDto getFacility(int facilityRefID) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URlCPE_SERVICE + "facility/" + facilityRefID, FacilityDto.class);
	}
	
	
	public static VehicleDto getVehicle(int id) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URlCPE_SERVICE + "vehicle/" + id, VehicleDto.class);
		
	}


	public static FireDto getFire(int id) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URlCPE_SERVICE + "fire/" +  id, FireDto.class);
	}


	public static List<Coord>  getItineraire(Coord depart, Coord arrive) {
		
		
		// TODO VERIFIER L'ORDRE LONGITUDE LATITUDE DANS LA REPRESENTATION POUR REQUETE MAIS AUSSI POUR CREATION COORD
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = MAPBOX_URL + depart.getLon() + "," + depart.getLat() + ";" + arrive.getLon() + "," + arrive.getLat() + "?geometries=geojson&access_token=" + MAPBOX_TOKEN;
		String reponse = restTemplate.getForObject(url, String.class);
		
		
		// Extraction des coordonnées gps de la reponse
		List<Coord> trajetCoord = new ArrayList<Coord>();
	
		JSONObject jsonReponse = new JSONObject(reponse);
		JSONArray arrayRoute =  jsonReponse.getJSONArray("routes");
		if (!arrayRoute.isEmpty())
		{
			JSONObject routeJson = new JSONObject( arrayRoute.get(0).toString());
			JSONObject geometrie = routeJson.getJSONObject("geometry");
		 
			JSONArray coordinates = geometrie.getJSONArray("coordinates");

			// conversion des coodonées GPS et conversion en obj Coords
			for (int i = 0; i < coordinates.length(); i++) {
				String[] coords = coordinates.get(i).toString().split(",");

				StringBuffer coord1String = new StringBuffer(coords[0]);
				StringBuffer coord2String = new StringBuffer(coords[1]);

				double lon = Double.parseDouble(coord1String.deleteCharAt(0).toString());
				double lat = Double.parseDouble(coord2String.deleteCharAt(coord2String.length() - 1).toString());

				trajetCoord.add(new Coord(lon, lat));

			}
			 
		 }
		 
		 
		 return trajetCoord;
		 
	}
	
	
	public static void main(String[] args)
	{
		Coord depart = new Coord(-84.518641,39.134270);
		Coord arrive = new Coord(-4.0,39.102779);
		System.out.println(VehicleTools.getItineraire(depart, arrive).toString());
	}


	public static void notifyFacilityEndFire(int fireId) {
	
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.put( URLFACILIY_ENDFIRE + fireId, "nada",String.class);
		
	}


	

}
