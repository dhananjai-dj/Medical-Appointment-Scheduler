package com.dsaProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.dsaProject.entities.Patient;
import com.dsaProject.entities.Schedule;
import com.dsaProject.entities.User;
import com.dsaProject.repositories.UserRepositoy;

import jakarta.mail.internet.MimeMessage;

@Component
public class GeneralService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(@NonNull String toMail, @NonNull String subject, @NonNull String body, @NonNull Schedule schedule)throws Exception {
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		String fileCopyName = schedule.getScheduleId()+".pdf";
		mimeMessageHelper.addTo(toMail);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(body);
		FileSystemResource fileSystemResource = new FileSystemResource("src/main/resources/static/AcknowledgementFiles/"+fileCopyName);
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource );
		
		javaMailSender.send(message);
		
	}

	public void createProfile(Model model, UserRepositoy userRepositoy, @NonNull Integer userId) {
		Optional<User> user = userRepositoy.findById(userId);
		User retrivedUser = user.get();
		model.addAttribute("user", retrivedUser);
		List<Patient> retrivedPatient = retrivedUser.getPatient();
		model.addAttribute("patientList",retrivedPatient);
	}
	
}
