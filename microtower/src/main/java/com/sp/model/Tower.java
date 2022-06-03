package com.sp.model;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sp.model.dto.FacilityDto;
import com.sp.model.dto.FireDto;

@Entity
@Table(name="TOWER")
public class Tower {
	Set<FireDto> fire_set;
	Set<FacilityDto> facility_set;
}
