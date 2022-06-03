package com.model;

public class VehicleDto {
	public static final int CREW_MEMBER_START_VALUE=-1;
	private Integer id;
	private double lon;
	private double lat;
	private VehicleType type;
	private LiquidType liquidType; // type of liquid effective to type of fire
	private float liquidQuantity; // total quantity of liquid
	private float fuel;		// total quantity of fuel
	private int crewMember;
	private Integer facilityRefID;
	
	public VehicleDto() {
		crewMember= CREW_MEMBER_START_VALUE;
		liquidType=LiquidType.ALL;
	}

	public VehicleDto(int id,double lon, double lat, com.model.VehicleType type,
			com.model.LiquidType liquidType, float liquidQuantity, float fuel,
			int crewMember, Integer facilityRefID) {
		super();
		this.id=id;
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.fuel = fuel;
		this.crewMember = crewMember;
		this.facilityRefID = facilityRefID;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
