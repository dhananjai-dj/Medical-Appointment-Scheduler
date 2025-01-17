package com.dsaProject.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.dsaProject.entities.Patient;
import com.dsaProject.entities.User;
import com.dsaProject.repositories.PatientRepository;
import com.dsaProject.repositories.UserRepositoy;

@Component
public class PatientService {

	public void createModel(Model model, String doctorId) {
		Patient patient = new Patient();
		model.addAttribute("patient", patient);
		model.addAttribute("doctorId", doctorId);
	}
	
	public void savePatient(String userName, Patient patient, UserRepositoy userRepositoy, PatientRepository patientRepository) {
		Optional<User> user = userRepositoy.findByUserName(userName);
		patient.setUser(user.get());
		patientRepository.save(patient);
	}

}
