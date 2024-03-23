package com.dsaProject.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsaProject.entities.User;
import com.dsaProject.repositories.UserRepositoy;
import com.dsaProject.service.GeneralService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {
	
	private UserRepositoy userRepositoy;
	private HttpSession session;
	private GeneralService generalService;
	
	@Autowired
	public GeneralController(UserRepositoy userRepositoy, HttpSession session, GeneralService generalService) {
		this.userRepositoy = userRepositoy;
		this.session = session;
		this.generalService = generalService;
	}
	
	@RequestMapping("/about")
	public String goAbout() {
		return "About";
	}
	
	@RequestMapping("/contact")
	public String goContact() {
		return "ContactUs";
	}
	
	@RequestMapping("/login-process")
	public String loginProcess(@RequestParam String userName, @RequestParam String password) throws Exception {
		if(userName == null || password == null)
			return "Login";
		Optional<User> user = userRepositoy.findByUserNamePassword(userName, password);
		if(user==null) {
			throw new Exception("Invalid User");
		}
		User validUser = user.get();
		session.setAttribute("userId", validUser.getUserId());
		session.setMaxInactiveInterval(120);
		return "Home";
	}
	
	@RequestMapping("/MyProfile")
	public String profileView(Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			return "Login";
		}
		generalService.createProfile(model, userRepositoy, userId);
		return "MyProfile";
	}
	}
