package com.sp.model;

public enum VehicleType {
	// https://lemonbin.com/types-of-fire-trucks/
	CAR(2, 2, 1, 10, 0.1F, 50, 5, 150.0F), 
	FIRE_ENGINE(4, 4, 5 ,50 , 0.5F, 60, 10, 110.0F), 
	PUMPER_TRUCK(10, 6, 20, 1000, 1, 500, 25, 70.0F), 
	WATER_TENDERS(10, 3, 20, 1000, 1, 500, 25, 110.0F), 
	TURNTABLE_LADDER_TRUCK(15, 6, 40, 1000, 3, 500, 30, 70.0F), 
	TRUCK(20, 8, 50, 2000, 3, 500, 40,110.0F);

	private int spaceUsedInFacility;
	private int vehicleCrewCapacity;
	private float efficiency; // need all crew member to reach full efficiency value between 0 and 10
	private float liquidCapacity; // total quantity of liquid
	private float liquidConsumption; // per second when use
	private float fuelCapacity; // total quantity of fuel
	private float fuelConsumption; // per km
	private float maxSpeed; // Km/Hour

	private VehicleType(int spaceUsedInFacility, int vehicleCrewCapacity, float efficiency,
			float liquidCapacity, float liquidConsumption, float fuelCapacity, float fuelConsumption, float maxSpeed) {
		this.spaceUsedInFacility = spaceUsedInFacility;
		this.vehicleCrewCapacity = vehicleCrewCapacity;
		this.efficiency = efficiency;
		this.liquidCapacity = liquidCapacity;
		this.liquidConsumption = liquidConsumption;
		this.fuelCapacity = liquidCapacity;
		this.efficiency = efficiency;
		this.maxSpeed=maxSpeed;
	}

	public int getSpaceUsedInFacility() {
		return this.spaceUsedInFacility;
	}

	public int getVehicleCrewCapacity() {
		return this.vehicleCrewCapacity;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	public float getLiquidCapacity() {
		return liquidCapacity;
	}

	public void setLiquidCapacity(float liquidCapacity) {
		this.liquidCapacity = liquidCapacity;
	}

	public float getLiquidConsumption() {
		return liquidConsumption;
	}

	public void setLiquidConsumption(float liquidConsumption) {
		this.liquidConsumption = liquidConsumption;
	}

	public float getFuelCapacity() {
		return fuelCapacity;
	}

	public void setFuelCapacity(float fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}

	public float getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public void setSpaceUsedInFacility(int spaceUsedInFacility) {
		this.spaceUsedInFacility = spaceUsedInFacility;
	}

	public void setVehicleCrewCapacity(int vehicleCrewCapacity) {
		this.vehicleCrewCapacity = vehicleCrewCapacity;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
}
