package com.dsaProject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsaProject.entities.Patient;
import com.dsaProject.repositories.PatientRepository;
import com.dsaProject.repositories.UserRepositoy;
import com.dsaProject.service.PatientService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PatientController {
	
	private PatientRepository patientRepository;
	private UserRepositoy userRepositoy;
	private HttpSession session;
	private PatientService patientService;
	
	@Autowired
	public PatientController(PatientRepository patientRepository, UserRepositoy userRepositoy, HttpSession session, PatientService patientService) {
		super();
		this.session = session;
		this.patientRepository = patientRepository;
		this.userRepositoy = userRepositoy;
		this.patientService = patientService;
	}
	

	@RequestMapping("/patient-register")
	public String patientRegistration(Model model, @RequestParam("doctorId") String doctorId) {
		if(doctorId == null)
			return "Home";
		if(session.getAttribute("userId")==null)
			return "Login";
		patientService.createModel(model, doctorId);
		return "PatientRegistration";
	}

	@PostMapping("/processPatient-form")
	public String patientRegister(@ModelAttribute("patient") Patient patient, @RequestParam("userName") String userName,@RequestParam("doctorId") String doctorId) {
		patientService.savePatient(userName, patient, userRepositoy, patientRepository);
		return "redirect:/schedule-register/"+doctorId+"/"+patient.getPatientId();
	}
	
}

