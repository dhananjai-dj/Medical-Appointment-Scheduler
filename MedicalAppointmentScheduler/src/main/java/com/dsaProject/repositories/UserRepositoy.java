package com.dsaProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dsaProject.entities.User;

public interface UserRepositoy extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.userName = ?1" )
	Optional<User> findByUserName(String userName);
	@Query("select u from User u where u.userName = ?1 and u.password = ?2")
	Optional<User> findByUserNamePassword(String userName, String password);
}
