package com.sp.model.dto;

public class FireDto {
	
	private Integer id;
	private String type;
	private float intensity;
	private float range;
	private double lon;
	private double lat;

	public FireDto() {
	}
	
	public FireDto(Integer id, String type, float intensity, float range, double lon, double lat) {
		super();
		this.id = id;
		this.type = type;
		this.intensity = intensity;
		this.range = range;
		this.lon = lon;
		this.lat = lat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getIntensity() {
		return intensity;
	}
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
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
	
	

}
