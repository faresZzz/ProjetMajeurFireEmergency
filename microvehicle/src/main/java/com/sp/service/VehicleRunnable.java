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
	
	private Coord facilityCoord;
	private Coord fireCoord;
	private Coord vehicleCoord;
	
	private VehicleDto interventionVehicle;
	private FireDto fire;
	
	private static  double R = 6372795.477598;
	private List<Coord> itineraire;
	
	public VehicleRunnable(VehicleDto interventionVehicle, FireDto fire, Coord facilityCoord, int niveauDeplacement)
	{
		this.isFireEnd = false;
		this.interventionEnd = false;
		this.niveauDeplacement = niveauDeplacement;
		
		this.fire = fire;
		this.interventionVehicle = interventionVehicle;
		
		this.facilityCoord = facilityCoord;
		this.fireCoord = new Coord(fire.getLon(), fire.getLat());
		this.vehicleCoord = new Coord(interventionVehicle.getLon(), interventionVehicle.getLat());
		this.itineraire = new ArrayList<>();
	}
	
	
	public void run()
	{
		
		if (this.niveauDeplacement == 3)
		{
			this.itineraire = VehicleTools.getItineraire(this.vehicleCoord, this.fireCoord);
		}
		// tant que le feu n'est pas terminer
		while (!this.isFireEnd)
		{
			// si le vehicule n'est pas arriver dans le range du feu, c'est a dire sur le lieux d'intervention
			if (GisTools.computeDistance2(this.fireCoord, this.vehicleCoord)  > this.fire.getRange())
			{
				// on deplace le vehicule suivant le niveau de deplacement choisi
				switch(this.niveauDeplacement)
				{
				case 1:
					this.teleportation(this.fireCoord);
					break;
					
				case 2: 
					this.deplacementLigneDroite(this.fireCoord);
					break;
					
				case 3:
					this.deplacementGPS(this.itineraire.get(0));
					this.itineraire.remove(0);
					break;
				}
				
				// on update le vehicle et le feu
				this.updateVehicle();
				this.updateFire();
			}
			
			try {
				Thread.sleep(1000); //sleep 1s;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		if (this.niveauDeplacement == 3)
		{
			this.itineraire = VehicleTools.getItineraire(this.vehicleCoord,this.facilityCoord);
		}
		
		// Une intervention est finie lorsque le vehicule est rentrer a la base
		while (!this.interventionEnd)
		{
			// si le vehicule n'est pas dans a la base( coord de la base)
			if (GisTools.computeDistance2(this.facilityCoord, this.vehicleCoord)  > 3) // chiffre choisi arbitrairement pour supposer que le vehicle est rentrer a la base
			{
				// on deplace le vehicule suivant le niveau de deplacement choisi
				switch(this.niveauDeplacement)
				{
				case 1:
					this.teleportation(this.facilityCoord);
					break;
					
				case 2: 
					this.deplacementLigneDroite(this.facilityCoord);
					break;
					
				case 3:
					this.deplacementGPS(this.itineraire.get(0));
					this.itineraire.remove(0);
					break;
				}
				
				// on update le vehicule
				this.updateVehicle();
				
			}
			else // si le vehicule est a la base alors l'intervention est terminer
			{
				this.interventionEnd = true;
				// on recharge le vehicle en fuel et liquide
				this.interventionVehicle.setFuel(this.interventionVehicle.getType().getFuelCapacity());
				this.interventionVehicle.setLiquidQuantity(this.interventionVehicle.getType().getLiquidCapacity());
			}
			
			
			try {
				Thread.sleep(1000); //sleep 1s;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		// potentiellement mettre le Tread en pause plutot que de le tuer
		
		// prevenir la facility de la fin de l'intervention
//		VehicleTools.notifyFacilityEndFire(this.fire);
		
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


	private void teleportation(Coord coordDestinationFinale) {
		// TODO Auto-generated method stub
		
		this.interventionVehicle.setLat(coordDestinationFinale.getLat());
		this.interventionVehicle.setLon(coordDestinationFinale.getLon());
		
		VehicleTools.deplacementVehicle(this.interventionVehicle);
		
	}	
	
	private void deplacementLigneDroite(Coord coordDestinationFinale) {
		
		Coord newCoord = this.calculNouvellesCoords(this.vehicleCoord, 20,  this.calculAngle(this.vehicleCoord, coordDestinationFinale)); // 20 distance en metre de depalcement arbitraire
		
		this.interventionVehicle.setLat(newCoord.getLat());
		this.interventionVehicle.setLon(newCoord.getLon());
		VehicleTools.deplacementVehicle(this.interventionVehicle);
	}
	
	private void deplacementGPS(Coord coordDestinationFinale) {
		
		this.interventionVehicle.setLat(coordDestinationFinale.getLat());
		this.interventionVehicle.setLon(coordDestinationFinale.getLon());
		
		VehicleTools.deplacementVehicle(this.interventionVehicle);
		
	}

	// Source methode de calcul : https://www.sunearthtools.com/fr/tools/distance.php#txtDist_5
	private Coord calculNouvellesCoords(Coord coordInit, int distance, double angle) // distance en metre angle en radian
	{
		
		
		double newLat = Math.asin( 
				Math.sin( coordInit.getLat()) * Math.cos( distance / VehicleRunnable.R ) +
				Math.cos( coordInit.getLat() ) * Math.sin( distance / VehicleRunnable.R ) * Math.cos( angle )
				);
	
		
		double newLon = coordInit.getLon() + Math.atan2(
				Math.sin(angle) * Math.sin(distance / VehicleRunnable.R) * Math.cos(coordInit.getLon()),
				Math.cos((double) (distance / VehicleRunnable.R)) - Math.sin(coordInit.getLon()) * Math.sin(newLat) );
		
		return new Coord(newLon, newLat);
	}
	
	private double calculAngle(Coord depart, Coord destination)
	{
		double deltaPhi = Math.log(
				Math.tan(destination.getLat() / 2 + Math.PI/4) / 
				Math.tan(depart.getLat() / 2 + Math.PI/4) 
				);
		 double deltaLon =Math.floorMod((long) Math.abs( depart.getLon() - destination.getLon()), 180) ;
		 
		 return Math.atan2(deltaLon, deltaPhi);
		
	}
	
}
