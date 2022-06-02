package com.sp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sp.model.Fire;

public interface FireRepository extends CrudRepository<Fire, Integer> {
	public Optional<Fire> findById(Integer id);
	public Iterable<Fire> findAll();
}
