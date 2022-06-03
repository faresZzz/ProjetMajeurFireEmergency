package com.model;

import java.util.HashSet;
import java.util.Set;

public class FacilityDto {
	
		private Integer id;
		private double lon;
		private double lat;
		
		private String name;
		private int maxVehicleSpace;
		private int peopleCapacity;
		
		private Set<Integer> vehicleIdSet;
		private  Set<Integer> peopleIdSet;
		
		private String teamUuid;
		
		public FacilityDto() {
			vehicleIdSet= new HashSet<Integer>();
			peopleIdSet= new HashSet<Integer>();
		}
		

		public FacilityDto(Integer id, double lon, double lat, String name, int maxVehicleSpace, int peopleCapacity,
				Set<Integer> vehicleIdSet, Set<Integer> peopleIdSet, String teamUuid) {
			super();
			this.id = id;
			this.lon = lon;
			this.lat = lat;
			this.name = name;
			this.maxVehicleSpace = maxVehicleSpace;
			this.peopleCapacity = peopleCapacity;
			this.vehicleIdSet = vehicleIdSet;
			this.peopleIdSet = peopleIdSet;
			this.teamUuid = teamUuid;
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
}
