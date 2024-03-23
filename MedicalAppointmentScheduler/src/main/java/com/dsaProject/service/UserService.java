package com.dsaProject.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.dsaProject.entities.User;
import com.dsaProject.repositories.UserRepositoy;

@Component
public class UserService {

	public void createModel(Model model) {
		User user = new User();
		model.addAttribute("user", user);
	}

	public void saveUser(UserRepositoy userRepositoy, @NonNull User user) {
		userRepositoy.save(user);
	}

}
