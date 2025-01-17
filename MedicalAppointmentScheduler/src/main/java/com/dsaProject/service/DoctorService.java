package com.dsaProject.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.dsaProject.entities.Address;
import com.dsaProject.entities.Doctor;
import com.dsaProject.repositories.DoctorRepository;

import jakarta.validation.Valid;

@Component
public class DoctorService {
	public void findAllDoctors(DoctorRepository doctorRepository, Model model){
		model.addAttribute("doctors", doctorRepository.findAll());
	}
	public void findBySpecialization(DoctorRepository doctorRepository, String specilization, Model model){
		List<Doctor> specialistDoctors = doctorRepository.findBySpecialization(specilization);
		Collections.sort(specialistDoctors,
				Comparator.comparing(Doctor::getExperience)
					.thenComparing(Doctor::getRating, Comparator.reverseOrder()
					)
				);
		model.addAttribute("doctors",specialistDoctors);
	}
	public void processForm(@Valid @NonNull Doctor doctor, MultipartFile imageFile, DoctorRepository doctorRepository, Model model) {
		if(!imageFile.isEmpty()) {
			try {
				
				 String uploadDirectory = "src/main/resources/static/doctorImages/";
			        Path directory = Paths.get(uploadDirectory);
			        Files.createDirectories(directory);
			        byte[] bytes = imageFile.getBytes();
			        @SuppressWarnings("null")
					String fileType[] = imageFile.getContentType().split("/");
			        String extension = fileType[1];
			        String path = uploadDirectory + doctor.getName()+doctor.getPhoneNumber()+"."+extension;
			        doctor.setFileLocation(path.substring(path.lastIndexOf("/")+1));
			        Path filePath = Paths.get(path);
			        Files.write(filePath, bytes);
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		}
																		
		 model.addAttribute(doctorRepository.save(doctor));
	}
	public void createModel(Model model) {
		    Address address = new Address();
	        Doctor doctor = new Doctor();
	        model.addAttribute("doctor", doctor);
	        model.addAttribute("address",address);
	}
}
