package com.megas.hospitalapp.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
	@RequestMapping(value={"/", "choose_role"}, method = RequestMethod.GET)
	public ModelAndView chooseRole(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		model.setViewName("home/choose_role");
		return model;
	}
	
	@PostMapping(value = "/role_selected")
	public RedirectView roleSelected(@RequestParam(value = "role", required = false) String role, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		if (role == null) {
			redirectAttributes.addFlashAttribute("actionMsg", "You haven't chosen a role!");
			return new RedirectView("/choose_role");
		}
		if (role.equalsIgnoreCase("doctor")) {
			return new RedirectView("/doctor_chosen");
		}else if (role.equalsIgnoreCase("patient")) {
			return new RedirectView("/patient_chosen");
		}
		redirectAttributes.addFlashAttribute("actionMsg", "You haven't chosen a role!");
		return new RedirectView("/choose_role");
	}
}