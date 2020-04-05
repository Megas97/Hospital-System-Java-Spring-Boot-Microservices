package com.megas.hospitalapp.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.megas.hospitalapp.app.models.Doctor;
import com.megas.hospitalapp.app.models.Patient;
import com.megas.hospitalapp.app.service.DoctorService;
import com.megas.hospitalapp.app.service.PatientService;

@Controller
public class PatientController {
	@Autowired
	PatientService patientService;
	
	@Autowired
	DoctorService doctorService;
	
	@GetMapping(value = "/patient_chosen")
	public ModelAndView patientChosen(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		model.setViewName("patient/patient_chosen");
		return model;
	}
	
	@GetMapping(value = "/patient_register")
	public ModelAndView patientRegister(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		Patient patient = new Patient();
		model.addObject("patient", patient);
		Iterable<Doctor> doctorsList = doctorService.getAllDoctors();
		model.addObject("doctorsList", doctorsList);
		model.setViewName("patient/patient_register");
		return model;
	}
	
	@PostMapping(value = "/patient_register")
	public ModelAndView register(@RequestParam(name = "doctorsList", required = false) String chosenDoctor, Patient patient, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		Patient patientExists = patientService.getPatientByIdentificationNumber(patient.getPatientIDNumber());
		if (patientExists != null) {
			bindingResult.rejectValue("patientIDNumber", "error.user", "This identification number is already in use!");
		}
		if (bindingResult.hasErrors()) {
			model.addObject("msg", "This identification number is already in use!");
			Iterable<Doctor> doctorsList = doctorService.getAllDoctors();
			model.addObject("doctorsList", doctorsList);
			model.setViewName("patient/patient_register");
		}else {
			if (chosenDoctor != null) {
				int chosenDoctorId = Integer.parseInt(chosenDoctor);
				Doctor doctor = doctorService.getDoctorById(chosenDoctorId);
				patient.setDoctorId(doctor.getDoctorId());
				patientService.createPatient(patient);
				model.addObject("msg", "Patient has been registered successfully!");
				model.addObject("patient", new Patient());
				Iterable<Doctor> doctorsList = doctorService.getAllDoctors();
				model.addObject("doctorsList", doctorsList);
				model.setViewName("patient/patient_register");
			}else {
				model.addObject("msg", "You must choose a GP from the dropdown list!");
				model.setViewName("patient/patient_register");
			}
		}
		return model;
	}
	
	@GetMapping(value = "/patient_login")
	public ModelAndView patientLogin(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		model.setViewName("patient/patient_login");
		return model;
	}
	
	@PostMapping(value = "/patient_login")
	public RedirectView login(@RequestParam(value = "idnumber-login", required = false) String patientIDNumber, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Patient patientExists = patientService.getPatientByIdentificationNumber(patientIDNumber);
		if (patientExists != null) {
			String patientName = patientExists.getPatientName();
			Cookie cookie = new Cookie("loggedInPatientId", patientIDNumber);
			response.addCookie(cookie);
			redirectAttributes.addFlashAttribute("loggedInPatientName", patientName);
			Patient patient = new Patient();
			patient.setPatientInsured(patientExists.isPatientInsured());
			redirectAttributes.addFlashAttribute("patient", patient);
			redirectAttributes.addFlashAttribute("patientDiagnosis", patientExists.getPatientDiagnosis());
			redirectAttributes.addFlashAttribute("patientMedicines", patientExists.getPatientMedicines());
			if (patientExists.getPatientSickdays() > 0) {
				redirectAttributes.addFlashAttribute("patientSickDays", patientExists.getPatientSickdays());
			}else {
				redirectAttributes.addFlashAttribute("patientSickDays", "");
			}
			if (patientExists.getPatientOldDiagnoses() != null) {
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", patientExists.getPatientOldDiagnoses());
			}else {
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
			}
			if (patientExists.getPatientOldMedicines() != null) {
				redirectAttributes.addFlashAttribute("patientOldMedicines", patientExists.getPatientOldMedicines());
			}else {
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
			}
			if (patientExists.getPatientOldSickDays() > 0) {
				redirectAttributes.addFlashAttribute("patientOldSickDays", patientExists.getPatientOldSickDays());
			}else {
				redirectAttributes.addFlashAttribute("patientOldSickDays", "");
			}
			return new RedirectView("/patient_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "The identification number is incorrect!");
			return new RedirectView("/patient_login");
		}
	}
	
	@GetMapping(value = "/patient_page")
	public ModelAndView patientPage(@CookieValue(value = "loggedInPatientId", defaultValue = "") String loggedInPatientId) {
		ModelAndView model = new ModelAndView();
		Patient patientExists = null;
		if (loggedInPatientId != "") {
			patientExists = patientService.getPatientByIdentificationNumber(loggedInPatientId);
		}
		if (patientExists != null) {
			model.addObject("patientDoctorId", patientExists.getDoctorId());
		}
		Iterable<Doctor> doctorsList = doctorService.getAllDoctors();
		model.addObject("doctorsList", doctorsList);
		model.setViewName("patient/patient_page");
		return model;
	}
	
	@PostMapping(value = "/update_patient_info")
	public RedirectView changePatientInfo(@ModelAttribute Patient patient, HttpServletResponse response, @CookieValue(value = "loggedInPatientId", defaultValue = "") String loggedInPatientId, @RequestParam("doctorsList") String chosenDoctor, RedirectAttributes redirectAttributes) {
		Patient patientExists = null;
		if (loggedInPatientId != "") {
			patientExists = patientService.getPatientByIdentificationNumber(loggedInPatientId);
		}
		String changedFields = "Changed fields: ";
		boolean somethingChanged = false;
		if (patientExists != null) {
			String patientName = patientExists.getPatientName();
			// Edit name
			if ((patient.getPatientName() != null) && (patient.getPatientName() != "")) {
				if (!patientExists.getPatientName().equalsIgnoreCase(patient.getPatientName())) {
					patientExists.setPatientName(patient.getPatientName());
					patientName = patientExists.getPatientName();
					changedFields += "Name | ";
					somethingChanged = true;
				}
			}
			// Edit identification number
			if ((patient.getPatientIDNumber() != null) && (patient.getPatientIDNumber() != "")) {
				if (!patientExists.getPatientIDNumber().equalsIgnoreCase(patient.getPatientIDNumber())) {
					patientExists.setPatientIDNumber(patient.getPatientIDNumber());
					Cookie cookie = new Cookie("loggedInPatientId", patient.getPatientIDNumber());
					response.addCookie(cookie);
					changedFields += "ID | ";
					somethingChanged = true;
				}
			}
			// Edit insured status
			if (patientExists.isPatientInsured() != patient.isPatientInsured()) {
				patientExists.setPatientInsured(patient.isPatientInsured());
				changedFields += "Insured | ";
				somethingChanged = true;
			}
			// Edit GP
			int chosenDoctorId = Integer.parseInt(chosenDoctor);
			if (patientExists.getDoctorId() != chosenDoctorId) {
				Doctor doctor = doctorService.getDoctorById(chosenDoctorId);
				patientExists.setDoctorId(doctor.getDoctorId());
				changedFields += "GP | ";
				somethingChanged = true;
			}
			if (somethingChanged == true) {
				patientService.createPatient(patientExists);
			}
			if (changedFields != "Changed fields: ") {
				redirectAttributes.addFlashAttribute("infoChangedMsg", changedFields);
			}
			redirectAttributes.addFlashAttribute("loggedInPatientName", patientName);
			Patient blankPatient = new Patient();
			blankPatient.setPatientInsured(patientExists.isPatientInsured());
			redirectAttributes.addFlashAttribute("patient", blankPatient);
			redirectAttributes.addFlashAttribute("patientDiagnosis", patientExists.getPatientDiagnosis());
			redirectAttributes.addFlashAttribute("patientMedicines", patientExists.getPatientMedicines());
			if (patientExists.getPatientSickdays() > 0) {
				redirectAttributes.addFlashAttribute("patientSickDays", patientExists.getPatientSickdays());
			}else {
				redirectAttributes.addFlashAttribute("patientSickDays", "");
			}
			if (patientExists.getPatientOldDiagnoses() != null) {
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", patientExists.getPatientOldDiagnoses());
			}else {
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
			}
			if (patientExists.getPatientOldMedicines() != null) {
				redirectAttributes.addFlashAttribute("patientOldMedicines", patientExists.getPatientOldMedicines());
			}else {
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
			}
			if (patientExists.getPatientOldSickDays() > 0) {
				redirectAttributes.addFlashAttribute("patientOldSickDays", patientExists.getPatientOldSickDays());
			}else {
				redirectAttributes.addFlashAttribute("patientOldSickDays", "");
			}
			return new RedirectView("/patient_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "You are not logged in!");
			return new RedirectView("/patient_login");
		}
	}
	
	@GetMapping(value = "/delete_patient")
	public RedirectView deletePatient(@CookieValue(value = "loggedInPatientId", defaultValue = "") String loggedInPatientId) {
		Patient patientExists = null;
		if (loggedInPatientId != "") {
			patientExists = patientService.getPatientByIdentificationNumber(loggedInPatientId);
		}
		if (patientExists != null) {
			patientService.deletePatientById(patientExists.getPatientId());
		}
		return new RedirectView("/patient_login");
	}
}