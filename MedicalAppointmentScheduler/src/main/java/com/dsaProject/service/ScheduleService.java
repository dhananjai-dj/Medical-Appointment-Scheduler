package com.dsaProject.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.dsaProject.entities.Doctor;
import com.dsaProject.entities.Patient;
import com.dsaProject.entities.Schedule;
import com.dsaProject.repositories.DoctorRepository;
import com.dsaProject.repositories.PatientRepository;
import com.dsaProject.repositories.ScheduleRepository;
import com.itextpdf.text.DocumentException;

@Component
public class ScheduleService {

	public void scheduleRegistrationService(Model model, String doctorId, String patientId, DoctorRepository doctorRepository) {
		Schedule schedule = new Schedule();
		model.addAttribute("schedule", schedule);
		Doctor doctor = doctorRepository.findById(Integer.parseInt(doctorId)).orElse(null);
		model.addAttribute("doctor",doctor);
		model.addAttribute("patientId", patientId);
	}
	
	@SuppressWarnings("null")
	public void proccessScheduleService(Schedule schedule, String doctorId, String patientId, PatientRepository patientRepository, ScheduleRepository scheduleRepository, AttachmentCreator attachmentCreator, GeneralService emailService, DoctorRepository doctorRepository, String subject, String body) throws Exception {
		LocalDateTime slot = schedule.getSlot();
		
		int retrivedDoctorId = Integer.parseInt(doctorId);
		Optional<Patient> optionalRequiredPatient = patientRepository.findById(Integer.parseInt(patientId));
		Optional<Doctor>optionalRequiredDoctor = doctorRepository.findById(retrivedDoctorId);
		
		if(optionalRequiredPatient.isPresent() && optionalRequiredDoctor.isPresent()) {
				
				
				Doctor requiredDoctor = optionalRequiredDoctor.get();
				Patient requiredPatient = optionalRequiredPatient.get();
				
				List<Schedule>doctorSchedules = requiredDoctor.getSchedule();
				List<Schedule>patientSchedules = requiredPatient.getSlotList();
				
				for(Schedule bookedSchedule : doctorSchedules) {
					if(bookedSchedule.getSlot().equals(slot)) {
						throw new Exception("Doctor not available");
					}
				}
				
				for(Schedule bookedSchedule : patientSchedules ) {
					if(bookedSchedule.getSlot().equals(slot)) {
						throw new Exception("You have already Booked on this schedule");
					}
				}
				
				schedule.setPatient(requiredPatient);
				schedule.setDoctor(requiredDoctor);
				doctorSchedules.add(schedule);
				patientSchedules.add(schedule);
				
				scheduleRepository.save(schedule);
				try {
					attachmentCreator.createAttachment(requiredDoctor, requiredPatient, schedule);
					final String subject2 = subject;
					if (subject2 != null) {
						final String body2 = body;
						if (body2 != null && requiredPatient.getEmailAddress()!=null) {
							emailService.sendMail(requiredPatient.getEmailAddress(), subject2, body2, schedule);
						} 
						}
						} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

}
	}