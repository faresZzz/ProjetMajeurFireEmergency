package com.sp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sp.model.Facility;

public interface FacilityRepository extends CrudRepository<Facility, Integer> {
	public Optional<Facility> findById(Integer id);
	public Iterable<Facility> findAll();
}
