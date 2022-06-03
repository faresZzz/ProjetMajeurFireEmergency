package com.sp.tools;


import org.springframework.web.client.RestTemplate;

import com.sp.model.Fire;

public class CommAPI {

	private static final String URLFIRE_SERVICE = "http://vps.cpe-sn.fr:8081/";
	
	private static final RestTemplate restTemplate = new RestTemplate();
	
	public static Fire[] getAllFire()
	{
//		RestTemplate restTemplate = new RestTemplate();
	
		Fire[] fireList = restTemplate.getForObject(URLFIRE_SERVICE +"fires" , Fire[].class);
		
		return fireList;
	}



	public static Fire getFireById(Integer fireId) {

			return restTemplate.getForObject(URLFIRE_SERVICE + "fire/"+ fireId, Fire.class);
	}

}
