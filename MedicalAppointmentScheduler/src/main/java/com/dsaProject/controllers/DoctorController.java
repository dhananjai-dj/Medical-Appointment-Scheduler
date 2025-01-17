package com.dsaProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dsaProject.entities.Doctor;
import com.dsaProject.repositories.DoctorRepository;
import com.dsaProject.service.DoctorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Validated
@Controller
public class DoctorController {

	private HttpSession session;
	private DoctorService doctorService;
	private DoctorRepository doctorRepository;

	@Value("${specializations}")
	private List<String> specializations;

	@Autowired
	public DoctorController(DoctorRepository doctorRepository, HttpSession session, DoctorService doctorService) {
		super();
		this.session = session;
		this.doctorRepository = doctorRepository;
		this.doctorService = doctorService;
	}

	@RequestMapping("/doctor-registration")
	public String goHome(Model model) {
		if (session.getAttribute("userId") == null) {
			return "Login";
		}
		doctorService.createModel(model);
		model.addAttribute("specializations", specializations);
		return "DoctorRegistration";
	}

	@PostMapping("/processDoctor-form")
	public String processDoctorForm(@Valid @ModelAttribute("doctor") @NonNull Doctor doctor, Model model,
			BindingResult bindingResult, @RequestParam("image") MultipartFile imageFile) {

		if (bindingResult.hasErrors()) {
			return "DoctorRegistration";
		}
		doctorService.processForm(doctor, imageFile, doctorRepository, model);
		return "redirect:/Home";
	}

	@GetMapping("/Home")
	public String displayDoctors(Model model) {
		doctorService.findAllDoctors(doctorRepository, model);
		return "Home";
	}

	@GetMapping("/searchDoctor-form")
	public String searchBySpeciality(Model model, @RequestParam String specilization) {
		doctorService.findBySpecialization(doctorRepository, specilization, model);
		return "Home";
	}

}
