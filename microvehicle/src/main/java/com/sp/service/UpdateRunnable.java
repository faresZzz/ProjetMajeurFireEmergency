package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sp.model.Fire;
import com.sp.repository.FireRepository;

/**
 * Permet de creer un thread qui recupere en permanence les infoamtions sur les feux
 * */
public class UpdateRunnable implements Runnable {
	private FireRepository frepo;
	private boolean isEnd = false;
	private static RestTemplate rest_template = new RestTemplate();
	
	// URLs
	private static String URL_GET_FIRE = "http://vps.cpe-sn.fr:8081/fire";

	public UpdateRunnable(FireRepository frepo) {
		this.frepo = frepo;
	}

	
	@Override
	public void run() {
		/* Initialisation des informations dans la base de donnée */
		frepo.saveAll( this.getHTTPFires());
		
		
		while (!this.isEnd) {
			
			try {
				
				this.updatedB(); 
				
				/*
				System.out.println("\n FINAL dB ");
				System.out.println(frepo.findAll().toString());
				System.out.println(" --------- ");
				*/
				
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}
	
	/**
	 * Compares the old db's elements with incomming changes
	 * */
	private void updatedB() {
		Collection<Fire> fires_http = this.getHTTPFires();
		ArrayList<Fire> fires_repo = (ArrayList<Fire>) frepo.findAll();
		HashMap<Integer,Fire> fires_repo_map = this.getHashMap(fires_repo);
		
		/*
		System.out.println(" ------ ");
		System.out.println(" HTTP ");
		System.out.print(fires_http);
		System.out.println("\n dB ");
		System.out.print(fires_repo_map);
		*/
		
		Collection<Integer> valid_id_fire = new ArrayList<Integer>();
		
		/* Pour chaque feu recut par http*/
		for (Fire fire_http : fires_http) 
		{
			if(fires_repo_map.containsKey(fire_http.hashCode())) 
			{
				valid_id_fire.add(fire_http.getId()); // Ajout de l'id du feu issus de la dB aux id validées
				frepo.delete(fire_http);
				frepo.save(fire_http);
			}
			else {
				System.out.println("\n New Fire \n");
				frepo.save(fire_http);
			}
		}
		
		/* Permet de supprimer les feu n'existant plus */
		for (Fire fire_repo : fires_repo) {
			if(!valid_id_fire.contains(fire_repo.getId())) {
				System.out.println("\n Fire Out! \n");
				frepo.delete(fire_repo);
			}
		}
		
	}
	
	/**
	 * Permet de convertir un array en hashmap en utilisant
	 * */
	public HashMap<Integer,Fire> getHashMap(ArrayList<Fire> fires){
		HashMap<Integer,Fire> ret = new HashMap<Integer,Fire>();
		
		for (Fire fire : fires) {
			ret.put(fire.hashCode(), fire);
		}
		
		return ret;
		
	}
	
	/**
	 * Requete pour recuperer la liste de tout les feux
	 * @PARAM 
	 * @RETURN Collection of fire
	 * */
	public Collection<Fire> getHTTPFires() {
		
		/**
		 * Permet d'appeller via un get http une liste en json convertie en liste d'objet java
		 * */
		ResponseEntity<ArrayList<Fire>> responseEntity = 
				  rest_template.exchange(
					URL_GET_FIRE,
				    HttpMethod.GET,
				    null,
				    new ParameterizedTypeReference<ArrayList<Fire>>() {}
				  );
		
		ArrayList<Fire> fireList = responseEntity.getBody();
		
		return fireList;
		
	}

	

}
