package com.sp.service;

import java.util.ArrayList;
import java.util.List;

import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.project.tools.GisTools;
import com.sp.tools.VehicleTools;

public class VehicleRunnable implements Runnable{

	private boolean isFireEnd;
	private boolean interventionEnd;
	private int niveauDeplacement;
	private int fireId; 
	
	private Coord facilityCoord;
	private Coord fireCoord;
	private Coord vehicleCoord;
	
	private VehicleDto interventionVehicle;
	private FireDto fire;
	private List<Coord> itineraire;
	
	public VehicleRunnable(VehicleDto interventionVehicle, FireDto fire, Coord facilityCoord, int niveauDeplacement)
	{
		this.isFireEnd = false;
		this.interventionEnd = false;
		this.niveauDeplacement = niveauDeplacement;
		
		this.fire = fire;
		this.fireId = fire.getId();
		this.interventionVehicle = interventionVehicle;
		
		this.facilityCoord = facilityCoord;
		this.fireCoord = new Coord(fire.getLon(), fire.getLat());
		this.vehicleCoord = new Coord(interventionVehicle.getLon(), interventionVehicle.getLat());
		this.itineraire = new ArrayList<>();
	}
	
	
	public void run()
	{
		if (this.niveauDeplacement == 2)
		{
			this.itineraire = this.calculNouvellesCoords(vehicleCoord, fireCoord, 20);
		}
		else if (this.niveauDeplacement == 3)
		{
			this.itineraire = VehicleTools.getItineraire(this.vehicleCoord, this.fireCoord);
			if (this.itineraire.isEmpty())
			{
				this.teleportation(facilityCoord);
				this.isFireEnd = true;
				this.interventionEnd = true;
			}
		}
		// tant que le feu n'est pas terminer
		while (!this.isFireEnd)
		{
			// si le vehicule n'est pas arriver dans le range du feu, c'est a dire sur le lieux d'intervention
			if (GisTools.computeDistance2(this.fireCoord, this.vehicleCoord)  > this.fire.getRange())
			{
				System.out.println("\n[$] Je vais au feu : " + this.interventionVehicle.getId());
				// on deplace le vehicule suivant le niveau de deplacement choisi
				switch(this.niveauDeplacement)
				{
				case 1:
					this.moveVehicle(this.fireCoord);
					break;
					
				case 2: 
					if (!this.itineraire.isEmpty())
					{
						this.moveVehicle(this.itineraire.get(0));
						this.itineraire.remove(0);
						
					}
					else
					{
						this.moveVehicle(this.fireCoord);

					}
					break;
					
				case 3:
					if (!this.itineraire.isEmpty())
					{
						this.moveVehicle(this.itineraire.get(0));
						this.itineraire.remove(0);
						
					}
					else
					{
						this.moveVehicle(this.fireCoord);

					}
					break;
				}
				
				
			}
			else
			{
				System.out.println("\n[$] J'eteins le feu :" + this.interventionVehicle.getId());
			}
			// on update le vehicle et le feu
			this.updateVehicle();
			this.updateFire();
			try {
				Thread.sleep(1000); //sleep 1s;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		//retour a la base
		if (this.niveauDeplacement == 2)
		{
			this.itineraire = this.calculNouvellesCoords(vehicleCoord, facilityCoord, 20);
		}
		else if (this.niveauDeplacement == 3)
		{
			this.itineraire = VehicleTools.getItineraire(this.vehicleCoord,this.facilityCoord);
		}
		
		// Une intervention est finie lorsque le vehicule est rentrer a la base
		while (!this.interventionEnd)
		{
			// si le vehicule n'est pas dans a la base( coord de la base)
			if (GisTools.computeDistance2(this.facilityCoord, this.vehicleCoord)  > 3) // chiffre choisi arbitrairement pour supposer que le vehicle est rentrer a la base
			{
				System.out.println("\n[$] Je vais à la caserne :" + this.interventionVehicle.getId());
				// on deplace le vehicule suivant le niveau de deplacement choisi
				switch(this.niveauDeplacement)
				{
				case 1:
					this.moveVehicle(this.facilityCoord);
					break;
					
				case 2: 
					if ( !this.itineraire.isEmpty())
					{
						this.moveVehicle(this.itineraire.get(0));
						this.itineraire.remove(0);
						
					}
					else
					{
						this.moveVehicle(this.facilityCoord);
						
					}
					break;
					
				case 3:
					if ( !this.itineraire.isEmpty())
					{
						this.moveVehicle(this.itineraire.get(0));
						this.itineraire.remove(0);
						
					}
					else
					{
						this.moveVehicle(this.facilityCoord);
						
					}
					break;
				}
				
				
				
			}
			else // si le vehicule est a la base alors l'intervention est terminer
			{
				this.interventionEnd = true;
				// on recharge le vehicle en fuel et liquide
				this.interventionVehicle.setFuel(this.interventionVehicle.getType().getFuelCapacity());
				this.interventionVehicle.setLiquidQuantity(this.interventionVehicle.getType().getLiquidCapacity());
			}
			
			// on update le vehicule
			this.updateVehicle();
			
			try {
				Thread.sleep(1000); //sleep 1s;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		// potentiellement mettre le Tread en pause plutot que de le tuer
		
		// prevenir la facility de la fin de l'intervention
		
		System.out.println("Mission finie :" + this.interventionVehicle.getId());
		VehicleTools.notifyFacilityEndFire(this.fireId);
		
		
	}


	private void updateVehicle() {
		// TODO Auto-generated method stub
		this.interventionVehicle = VehicleTools.getVehicle( this.interventionVehicle.getId());
		this.vehicleCoord.setLat(this.interventionVehicle.getLat());
		this.vehicleCoord.setLon(this.interventionVehicle.getLon());
	}


	private void updateFire() {
		
		this.fire = VehicleTools.getFire(this.fire.getId());
		
		if (this.fire != null)
		{
			this.fireCoord.setLat(this.fire.getLat());
			this.fireCoord.setLon(this.fire.getLon());
		}
		else
		{
			this.isFireEnd = true;
		}
		
	}


	private void moveVehicle(Coord coordDestinationFinale) {
		// TODO Auto-generated method stub
		
		this.interventionVehicle.setLat(coordDestinationFinale.getLat());
		this.interventionVehicle.setLon(coordDestinationFinale.getLon());
		
		VehicleTools.deplacementVehicle(this.interventionVehicle);
		
	}	
	
	
	private  List<Coord> calculNouvellesCoords(Coord coordInit, Coord coodFinale, int nbDeplacement) //
	{
		
		List<Coord> deplacementList = new ArrayList<>();
		
		for (int i = 0 ; i<= nbDeplacement; i++)
		{
			
			double newLat = coordInit.getLat() + ((float)i/ nbDeplacement) * ( coodFinale.getLat() - coordInit.getLat()) ;
			double newLon = coordInit.getLon() + ((float)i/nbDeplacement) * ( coodFinale.getLon() - coordInit.getLon()) ;
	
			System.out.println("Coord : " + newLat +" : " + newLon + "\n\n");
			deplacementList.add(new Coord(newLon, newLat));
		}
		
		return deplacementList;
	}
	
	
}
