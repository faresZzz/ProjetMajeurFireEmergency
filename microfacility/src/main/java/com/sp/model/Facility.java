package com.sp.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Facility")
public class Facility {
	@Id
	private Integer id;
	private double lon;
	private double lat;
	
	private String name;
	private int maxVehicleSpace;
	private int peopleCapacity;
	
	private Set<Integer> vehicleIdSet;
	private  Set<Integer> peopleIdSet;
	
	private static String teamUuid = "0eb29fc1-d666-4dd6-9a6e-933f29f87689/164";
	

	public Facility(Integer id, double lon, double lat, String name, int maxVehicleSpace, int peopleCapacity,
			Set<Integer> vehicleIdSet, Set<Integer> peopleIdSet) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.name = name;
		this.maxVehicleSpace = maxVehicleSpace;
		this.peopleCapacity = peopleCapacity;
		this.vehicleIdSet = vehicleIdSet;
		this.peopleIdSet = peopleIdSet;
	}
	
	public Facility() {
		super();
		this.id = 0;
		this.lon = 0.0f;
		this.lat = 0.0f;
		this.name = "";
		this.maxVehicleSpace = 0;
		this.peopleCapacity = 0;
		this.vehicleIdSet = new HashSet<Integer>();
		this.peopleIdSet = new HashSet<Integer>();
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxVehicleSpace() {
		return maxVehicleSpace;
	}

	public void setMaxVehicleSpace(int maxVehicleSpace) {
		this.maxVehicleSpace = maxVehicleSpace;
	}

	public int getPeopleCapacity() {
		return peopleCapacity;
	}

	public void setPeopleCapacity(int peopleCapacity) {
		this.peopleCapacity = peopleCapacity;
	}

	public Set<Integer> getVehicleIdSet() {
		return vehicleIdSet;
	}

	public void setVehicleIdSet(Set<Integer> vehicleIdSet) {
		this.vehicleIdSet = vehicleIdSet;
	}

	public Set<Integer> getPeopleIdSet() {
		return peopleIdSet;
	}

	public void setPeopleIdSet(Set<Integer> peopleIdSet) {
		this.peopleIdSet = peopleIdSet;
	}

	public String getTeamUuid() {
		return teamUuid;
	}

	public void setTeamUuid(String teamUuid) {
		this.teamUuid = teamUuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Facility other = (Facility) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Facilty [id=" + id + ", lon=" + lon + ", lat=" + lat + ", name=" + name + ", maxVehicleSpace="
				+ maxVehicleSpace + ", peopleCapacity=" + peopleCapacity + ", vehicleIdSet=" + vehicleIdSet
				+ ", peopleIdSet=" + peopleIdSet + ", teamUuid=" + teamUuid + "]";
	}
	
	
}
