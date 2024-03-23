package com.dsaProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsaProject.entities.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
