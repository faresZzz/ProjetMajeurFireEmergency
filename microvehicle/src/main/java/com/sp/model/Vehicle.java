package com.sp.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;

@Entity
@Table(name="VEHICLE")
public class Vehicle {
	@Id
	private Integer id;
	private double lon;
	private double lat;
	private VehicleType type;
	private LiquidType liquidType; // type of liquid effective to type of fire
	private float liquidQuantity; // total quantity of liquid
	private float fuel;		// total quantity of fuel
	private int crewMember;
	private Integer facilityRefID;
	
	public Vehicle(Integer id, double lon, double lat, VehicleType type, LiquidType liquidType, float liquidQuantity,
			float fuel, int crewMember, Integer facilityRefID) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.fuel = fuel;
		this.crewMember = crewMember;
		this.facilityRefID = facilityRefID;
	}

	public Vehicle() {
		super();
		this.id = 0;
		this.lon = 0.0;
		this.lat = 0.0;
		this.type = VehicleType.FIRE_ENGINE;
		this.liquidType = LiquidType.ALL;
		this.liquidQuantity = 0.0f;
		this.fuel = 0.0f;
		this.crewMember = 0;
		this.facilityRefID=0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public LiquidType getLiquidType() {
		return liquidType;
	}

	public void setLiquidType(LiquidType liquidType) {
		this.liquidType = liquidType;
	}

	public float getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(float liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}

	public int getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(int crewMember) {
		this.crewMember = crewMember;
	}

	public Integer getFacilityRefID() {
		return facilityRefID;
	}

	public void setFacilityRefID(Integer facilityRefID) {
		this.facilityRefID = facilityRefID;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", lon=" + lon + ", lat=" + lat + ", type=" + type + ", liquidType=" + liquidType
				+ ", liquidQuantity=" + liquidQuantity + ", fuel=" + fuel + ", crewMember=" + crewMember
				+ ", facilityRefID=" + facilityRefID + "]";
	}

	@Override
	public int hashCode() {
		Integer result = (id == null) ? 0 : id.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
}
