package com.sp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.HttpClientErrorException;

import com.project.model.dto.Coord;
import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.tools.GisTools;
import com.sp.tools.TowerTools;

public class TowerRunnable implements Runnable{

	private boolean finishOperations = false;
	private boolean facilityAvailable = true;
	
	private int facilityId;
	private FacilityDto facility;
	private Coord facilityCood;
	
	private Map<Integer, FireDto> pendingFireList;
	private Map<Integer, FireDto> inProcessFireList;
	
	
	public TowerRunnable(int facilityId)
	{
		this.setFacilityId(facilityId);
		this.facility = this.getFacility( facilityId);
		this.facilityCood = new Coord(this.facility.getLon(), this.facility.getLat());
		this.pendingFireList = new HashMap<Integer, FireDto>();
		
		
	}
	
	
	private FacilityDto getFacility(int facilityId) {
		// TODO Auto-generated method stub
		return TowerTools.getFacility( facilityId);
	}
	
	public void setFireList(Map<Integer, FireDto> newList)
	{
		this.pendingFireList = newList;
	}
	
	public void addFire(FireDto newFire)
	{
		this.pendingFireList.put(newFire.getId(), newFire);
	}
	
	public void endedFire(int finishedFireId)
	{
		if (this.inProcessFireList.get(finishedFireId) != null)
		{
			this.inProcessFireList.remove(finishedFireId);
			this.facilityAvailable = true;
			
		}
	}
	
	@Override
	public void run() {

		while(! this.finishOperations)
		{
			if (! this.pendingFireList.isEmpty())
			{
				
				FireDto newFireInProcess = this.getClosestFire();
				if (newFireInProcess !=null)
				{
					if (this.pendingFireList.remove(newFireInProcess.getId(), newFireInProcess))
					{
						this.inProcessFireList.put(newFireInProcess.getId(), newFireInProcess);
						if (this.sendFire(newFireInProcess, this.facility))
						{
							System.out.println("\n[$] "+java.time.LocalTime.now()+" Facility "+this.facility.getId()+" is assigned fire "+newFireInProcess.getId());
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						else
						{ // methode temporaire afin de susprendre l'execution du Thread en attandant que la facility soit available
							// pour eviter de lui envoyer plein de requetes inutilement.
							this.facilityAvailable = false;
							while( !this.facilityAvailable)
							{
								try {
									Thread.sleep(10000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				}
			}
			
			
			
			
		}
	}
	
	public void setFinishOperations(boolean finishOperations) {
		this.finishOperations = finishOperations;
	}


	/**
	 * Permet d'envoyer le feu à la facility voulut
	 * */
	public Boolean sendFire(FireDto fi, FacilityDto fa) { 
		Boolean ret = false;
		try {

			/* Utiliser nos outils pour faire une requete POST a la caserne avec l'info du feu*/
			ret = TowerTools.sendFire(fa.getId(), fi);
			
		}
		/* Gestion des exceptions HTTP*/
		
		catch( HttpClientErrorException httpClientErrorException) {
			System.out.println(httpClientErrorException.getResponseBodyAsString()); 
		}
		return ret;
	}
	
	/**
	 * Permet de calculer le feu le plus pres de la caserne mais egalement de supprimer les feu jugé trop loin
	 * @return closest fire id
	 */
	
	private FireDto getClosestFire()
	{
		float min  = 100000000000000.f;
		int limiteDistanceAction = 10000; // 10km autour de la caserne
		FireDto closestfireId = null;
		
		for (Map.Entry<Integer, FireDto> fire : this.pendingFireList.entrySet())
		{
			int distance = GisTools.computeDistance2(facilityCood, new Coord(fire.getValue().getLon(), fire.getValue().getLat()));
			
			if (distance > limiteDistanceAction) // suppression de feu trop loin
			{
				this.pendingFireList.remove(fire.getKey(), fire.getValue());
				continue;
			}
			if (distance < min)
			{
				closestfireId = fire.getValue();
				min = distance;
				
			}
		}
		
		return closestfireId;
	}


	
	public int getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

}
