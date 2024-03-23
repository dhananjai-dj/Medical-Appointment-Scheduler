package com.dsaProject.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
	String diagnosis;
	LocalDateTime lastVist;
	@ElementCollection
	List<String> alergies;
	@ElementCollection
	List<String> surgeries;
	@ElementCollection
	List<String> medications;
}
