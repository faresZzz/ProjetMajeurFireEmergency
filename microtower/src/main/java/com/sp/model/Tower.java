package com.sp.model;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireType;


@Entity
@Table(name="TOWER")
public class Tower {
	Set<FireType> fire_set;
	Set<FacilityDto> facility_set;
}
