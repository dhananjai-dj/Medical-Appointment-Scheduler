package com.dsaProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dsaProject.entities.User;
import com.dsaProject.repositories.UserRepositoy;
import com.dsaProject.service.UserService;

@Controller
public class UserController {
	
	private UserRepositoy userRepositoy;
	private UserService userService;

	@Autowired
	public UserController(UserRepositoy userRepositoy, UserService userService) {
		super();
		this.userRepositoy = userRepositoy;
		this.userService = userService;
	}

	@GetMapping("/user-registration")
	public String registerUser(Model model) {
		userService.createModel(model);
		return "UserRegistration";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		return "Login";
	}

	@PostMapping("/processUser")
	public String processUser(@ModelAttribute("user") User user, Model model) {
		if (user == null)
			return "error";
		userService.saveUser(userRepositoy, user);
		return "redirect:/Home";
	}
}
