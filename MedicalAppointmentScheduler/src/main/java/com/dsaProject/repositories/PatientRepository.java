package com.dsaProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dsaProject.entities.Patient;


public interface PatientRepository extends JpaRepository<Patient, Integer> {

	@Query("select p from Patient p where p.user = ?1" )
	List<Patient> findUser(int userId);
}
