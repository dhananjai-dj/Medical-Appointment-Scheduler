package com.dsaProject.MedicalAppointmentScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.dsaProject.entities" })
@ComponentScan(basePackages = { "com.dsaProject.controllers", "com.dsaProject.repositories", "com.dsaProject.service" })
@EnableJpaRepositories(basePackages = "com.dsaProject.repositories")
public class MedicalAppointmentSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalAppointmentSchedulerApplication.class, args);

	}

}
