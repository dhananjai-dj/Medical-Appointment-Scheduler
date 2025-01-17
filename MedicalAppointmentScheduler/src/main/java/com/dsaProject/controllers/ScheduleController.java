package com.dsaProject.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.dsaProject.entities.Schedule;
import com.dsaProject.repositories.DoctorRepository;
import com.dsaProject.repositories.PatientRepository;
import com.dsaProject.repositories.ScheduleRepository;
import com.dsaProject.service.AttachmentCreator;
import com.dsaProject.service.GeneralService;
import com.dsaProject.service.ScheduleService;




@Controller
public class ScheduleController {

	@Value("${subject}")
	String subject;
	@Value("${body}")
	String body;
	
	private GeneralService emailService;
	private ScheduleRepository scheduleRepository;
	private PatientRepository patientRepository;
	private DoctorRepository doctorRepository;
	private AttachmentCreator attachmentCreator;
	private ScheduleService scheduleService; 
	
	@Autowired
	public ScheduleController(ScheduleRepository scheduleRepository, PatientRepository patientRepository,  
							  DoctorRepository doctorRepository, AttachmentCreator attachmentCreator, GeneralService emailService, ScheduleService scheduleService) {
		super();
		this.scheduleRepository = scheduleRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.attachmentCreator = attachmentCreator;
		this.emailService = emailService;
		this.scheduleService = scheduleService;
	}
	
	@GetMapping("/schedule-register/{doctorId}/{patientId}")
	public String scheduleRegistration(Model model, @PathVariable("doctorId") String doctorId, @PathVariable("patientId") String patientId) {
		Schedule schedule = new Schedule();
		model.addAttribute("schedule", schedule);
		model.addAttribute("doctorId", doctorId);
		scheduleService.scheduleRegistrationService(model, doctorId, patientId, doctorRepository);
		return "ScheduleRegisteration";
	}
	



	@PostMapping("processSchedule-form")
	public String processSchedule(@ModelAttribute("Schedule")Schedule schedule, @RequestParam("doctorId")String doctorId, @RequestParam("patientId") String patientId) throws Exception {
		
		scheduleService.proccessScheduleService(schedule, doctorId, patientId, patientRepository, scheduleRepository, attachmentCreator, emailService, doctorRepository, subject, body);
		
		return "redirect:/Home";			
	}
}
