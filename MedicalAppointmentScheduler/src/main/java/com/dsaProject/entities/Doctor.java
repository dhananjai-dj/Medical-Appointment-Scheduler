package com.dsaProject.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "Name",nullable = false)
	
	@NotBlank
	String name;
	
	@Column(name="Phone_Number",unique = true)
	String phoneNumber;
	

	String specialization;
	
	String hospitalName;
	
	String fileLocation;
	
	@OneToMany(mappedBy = "doctor")
	List<Schedule> schedule = new ArrayList<Schedule>();
	
	int experience;
	
	int rating;
	@Embedded
	Address location;
	
}
