package com.sp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sp.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
	public Optional<Vehicle> findById(Integer id);
	public Iterable<Vehicle> findAll();
}
