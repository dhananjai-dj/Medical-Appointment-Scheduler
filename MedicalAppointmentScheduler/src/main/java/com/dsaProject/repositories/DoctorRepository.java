package com.dsaProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dsaProject.entities.Doctor;

@RepositoryRestResource(path = "doctors")
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	@Query("select d from Doctor d where d.specialization = ?1")
    List<Doctor>findBySpecialization(String specialization);
}
