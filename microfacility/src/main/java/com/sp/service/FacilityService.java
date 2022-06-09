package com.sp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.Coord;
import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.model.dto.FireType;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.tools.GisTools;
// 164 151
import com.sp.tools.FacilityTools;

import net.bytebuddy.asm.Advice.This;

@Service
public class FacilityService {
	
	private Collection<Integer> list_id = new LinkedHashSet<Integer>();
	
	private List<VehicleDto> listVehicle = new ArrayList<VehicleDto>();
	
	private List<Integer> listVehicleEnInterventions = new ArrayList<Integer>() ;
	
	
	private static List<Integer> LIST_FACILITY_ID = new ArrayList<Integer>( List.of(664791,664813));
	private Map <Integer, Set<VehicleDto>> vehicleByFacility = new HashMap<Integer, Set<VehicleDto>>(); //Map (idFaciliy  List(idVehicle))
	private Map<Integer, VehicleDto> listeVehicleEnInter = new HashMap<Integer, VehicleDto>();    // Map fireId, vehicleId
	private  static int niveauDifficulter = 1;

	
 
	
	public void InitFacilities()
	{
		for(int facilityId : LIST_FACILITY_ID)
		{
			FacilityDto facility = FacilityTools.getFacility(facilityId);
			System.out.println("\n[$] "+java.time.LocalTime.now()+" Facility "+facility.getId()+" has been initialised at :"+facility.getLat()+"|"+facility.getLon());
			Set<VehicleDto> vehicleSet = new HashSet<VehicleDto>();
			for (Integer vehicleId : facility.getVehicleIdSet())
			{
				vehicleSet.add(FacilityTools.getVeHicleById(vehicleId));
			}
			this.vehicleByFacility.put(facilityId, vehicleSet );
			for (Integer id_fac : this.vehicleByFacility.keySet()) {
				for (VehicleDto v : this.vehicleByFacility.get(id_fac)) {
					System.out.println("\n[$] "+java.time.LocalTime.now()+" Facility "+id_fac+" has the vehicle "+v.getId());
				}
				
			}
			
		}
	}
	
	public boolean envoyerVehicleEnIntervention(int facilityId, FireDto fire)
	{
		if (this.vehicleByFacility.isEmpty())
		{
			this.InitFacilities();
		}
		
		boolean missionPrise = false;
		
		switch(niveauDifficulter)
		{
		case 1 :  
			missionPrise = this.choixCamion1(facilityId, fire);
			break;
			
		case 2 :  
			missionPrise = this.choixCamion2(facilityId, fire);
			break;
			
		case 3 :  
			missionPrise = this.choixCamion3(facilityId, fire);
			break;
		}
		
		return missionPrise;
		
	}
	
	// Ca aurait ete mieux de reutiliser les methodes au fur et a mesure car choixCamion2 + choixCamion1 + liquide, etc
	// mais pas trouvé comment faire
	
	private boolean choixCamion1(int facilityId, FireDto fire) // choix Camion premier camion sur notre list
	{
		boolean affectationVehicle = false;
		
		Set<VehicleDto> listvehicleId = vehicleByFacility.get(facilityId);
		
		for (VehicleDto vehicle : listvehicleId)// pour chaque vehicle dans la caserne
		{
			// !!!!! attention pas sur de ce test card type vehicleDTO donc depend de comment il fait sont test et si l'on a pas fait de copie au milieux
			if (!this.listeVehicleEnInter.containsValue(vehicle)) // on test s'il n'est pas deja en intervention
			{
				System.out.println("J'envoie le camion : " + vehicle.getId() +" au feu " + fire.getId());
				this.listeVehicleEnInter.put(fire.getId(), vehicle);
				FacilityTools.envoyerVehicleEnMission(vehicle.getId(), fire);
				affectationVehicle = true;
				return affectationVehicle;
			}
		}
		
		return affectationVehicle;
	}
	
	private boolean choixCamion2(int facilityId, FireDto fire) // Choix camion par type de liquide 
	{
		boolean affectationVehicle = false;
		Set<VehicleDto> listvehicle = vehicleByFacility.get(facilityId);
		
		float efficiencyMax =  0.0f;
		VehicleDto choosedVehicle = null;
		for (VehicleDto vehicle : listvehicle)// pour chaque vehicle dans la caserne
		{
			// !!!!! attention pas sur de ce test card type vehicleDTO donc depend de comment il fait sont test et si l'on a pas fait de copie au milieux
			if (!this.listeVehicleEnInter.containsValue(vehicle)) // on test s'il n'est pas deja en intervention
			{
				
				if ( vehicle.getLiquidType().getEfficiency(vehicle.getLiquidType().toString()) > efficiencyMax)// on cherche quel camion a le liquide avec la meilleur efficience
				{
					efficiencyMax = vehicle.getLiquidType().getEfficiency(vehicle.getLiquidType().toString());
					choosedVehicle = vehicle;
				}
				
			}
		}
		if (choosedVehicle != null)
		{
			this.listeVehicleEnInter.put(fire.getId(), choosedVehicle);
			FacilityTools.envoyerVehicleEnMission(choosedVehicle.getId(), fire);
			affectationVehicle = true;			
		}
		
		
		return affectationVehicle;
	}
	
	private boolean choixCamion3(int facilityId, FireDto fire) // choix camion par type de liquide et quantité liquide
	{
		boolean affectationVehicle = false;
		Set<VehicleDto> listvehicle = vehicleByFacility.get(facilityId);
		
		float efficiencyMax =  0.0f;
		VehicleDto choosedVehicle = null;
		for (VehicleDto vehicle : listvehicle)// pour chaque vehicle dans la caserne
		{
			// !!!!! attention pas sur de ce test card type vehicleDTO donc depend de comment il fait sont test et si l'on a pas fait de copie au milieux
			if (!this.listeVehicleEnInter.containsValue(vehicle)) // on test s'il n'est pas deja en intervention
			{
				
				if ( vehicle.getLiquidType().getEfficiency(vehicle.getLiquidType().toString()) > efficiencyMax)// on cherche quel camion a le liquide avec la meilleur efficience
				{
					if (this.checkIfLiquidSufficient(vehicle, fire))
					{
						efficiencyMax = vehicle.getLiquidType().getEfficiency(vehicle.getLiquidType().toString());
						choosedVehicle = vehicle;
					}
				}
				
			}
		}
		if (choosedVehicle != null)
		{
			this.listeVehicleEnInter.put(fire.getId(), choosedVehicle);
			FacilityTools.envoyerVehicleEnMission(choosedVehicle.getId(), fire);
			affectationVehicle = true;			
		}
		
		
		return affectationVehicle;
	}
	
	private boolean choixCamion4(int facilityId, FireDto fire) // choix camion3 + reserve essence
	{
		return false;
	}
	
	
	private boolean checkIfLiquidSufficient(VehicleDto vehicle, FireDto fire )
	{
		float fireIntensity = fire.getIntensity();
		String fireType = fire.getType();

		LiquidType vehicleLiquidType = vehicle.getLiquidType();
		float vehicleLiquidQuantity = vehicle.getLiquidQuantity();
		
		float quantityLiquidNeedded = 0.0f;
		/// calcul pour obtenir la quantité de liquide du type du camion necessaire pour eteindre le feu
		/*
		 * 𝒇.𝑖𝑛𝑡𝑒𝑛𝑠𝑖𝑡𝑦=𝒇.𝑖𝑛𝑡𝑒𝑛𝑠𝑖𝑡𝑦−𝒗.𝑒𝑓𝑓𝑖𝑐𝑖𝑒𝑛𝑐𝑦×𝒗.𝑙𝑖𝑞𝑢𝑖𝑑𝑇𝑦𝑝𝑒.𝑒𝑓𝑓𝑖𝑐𝑖𝑒𝑛𝑐𝑦(𝒇.𝑡𝑦𝑝𝑒)×𝑓𝑖𝑟𝑒𝑈𝑝𝑑𝑎𝑡𝑒𝐶𝑜𝑛𝑓𝑖𝑔.𝑎𝑡𝑡𝑒𝑛𝑢𝑎𝑡𝑖𝑜𝑛𝐹𝑎𝑐𝑡𝑜𝑟
		 */
		
		return quantityLiquidNeedded < vehicleLiquidQuantity;
	}
	
	private boolean checkIfFuelSufficient(VehicleDto vehicle, FireDto fire)
	{
		// on suppose que la distance a vol d'oiseau suffit sinon il faut recupere la distance a parcourir avec MapBox et c'est chiant
		
		int distance  = GisTools.computeDistance2(new Coord(vehicle.getLon(), vehicle.getLat()),new Coord(fire.getLon(), fire.getLat()) );
		float fuelQuantityNeedded = vehicle.getType().getFuelConsumption() * (distance / 1000);
		
		return fuelQuantityNeedded < vehicle.getLiquidQuantity();
	}


	public void endFire(int idFire) {
		
		if (this.listeVehicleEnInter.containsKey(idFire))
		{
			// suppresion du feu et donc liberation du vehicle
			this.listeVehicleEnInter.remove(idFire); 
			// on previent la tour de controle que le feu est fini
			FacilityTools.updateControlTower(idFire);
		}
	
	}
	

	

}
