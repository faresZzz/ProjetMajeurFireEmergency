package com.sp.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FIRE")
public class Fire {
	@Id
	private Integer id;
	private Float intensity;
	private Double lat;
	private Double lon;
	private Float range;
	private String type;
	
	public Fire(Integer id, Float intensity, double lat, double lon, float range, String type) {
		super();
		this.id = id;
		this.intensity = intensity;
		this.lat = lat;
		this.lon = lon;
		this.range = range;
		this.type = type;
	}
	
	public Fire() {
		super();
		this.id=0;
		this.intensity = 0.0f;
		this.lat = 0.0;
		this.lon = 0.0;
		this.range = 0.0f;
		this.type = "";
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getIntensity() {
		return intensity;
	}

	public void setIntensity(Float intensity) {
		this.intensity = intensity;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Fire [id=" + id + ", intensity=" + intensity + ", lat=" + lat + ", lon=" + lon + ", range=" + range
				+ ", type=" + type + "]";
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
		Fire other = (Fire) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
	

}
