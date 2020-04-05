package com.megas.hospitalapp.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class DoctorController {
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	PatientService patientService;
	
	@GetMapping(value = "/doctor_chosen")
	public ModelAndView doctorChosen(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		model.setViewName("doctor/doctor_chosen");
		return model;
	}
	
	@GetMapping(value = "/doctor_register")
	public ModelAndView doctorRegister(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		Doctor doctor = new Doctor();
		model.addObject("doctor", doctor);
		model.setViewName("doctor/doctor_register");
		return model;
	}
	
	@PostMapping(value = "/doctor_register")
	public ModelAndView register(@Valid Doctor doctor, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		Doctor doctorExists = doctorService.getDoctorByIdentificationNumber(doctor.getDoctorIDNumber());
		if (doctorExists != null) {
			bindingResult.rejectValue("doctorIDNumber", "error.user", "This identification number is already in use!");
		}
		if (bindingResult.hasErrors()) {
			model.addObject("msg", "This identification number is already in use!");
			model.setViewName("doctor/doctor_register");
		}else {
			doctorService.createDoctor(doctor);
			model.addObject("msg", "Doctor has been registered successfully!");
			model.addObject("doctor", new Doctor());
			model.setViewName("doctor/doctor_register");
		}
		return model;
	}
	
	@GetMapping(value = "/doctor_login")
	public ModelAndView doctorLogin(HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Cookie doctorCookie = new Cookie("loggedInDoctorId", null);
		response.addCookie(doctorCookie);
		Cookie patientCookie = new Cookie("loggedInPatientId", null);
		response.addCookie(patientCookie);
		model.setViewName("doctor/doctor_login");
		return model;
	}
	
	@PostMapping(value = "/doctor_login")
	public RedirectView login(@RequestParam(value = "idnumber-login", required = false) String doctorIDNumber, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = doctorService.getDoctorByIdentificationNumber(doctorIDNumber);
		if (doctorExists != null) {
			String doctorName = doctorExists.getDoctorName();
			Cookie cookie = new Cookie("loggedInDoctorId", doctorIDNumber);
			response.addCookie(cookie);
			redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
			redirectAttributes.addFlashAttribute("doctor", new Doctor());
			Iterable<Patient> patientsList = patientService.getAllPatients();
			List<Patient> doctorPatientsList = new ArrayList<Patient>();
			for (Patient patient : patientsList) {
				if (patient.getDoctorId() == doctorExists.getDoctorId()) {
					doctorPatientsList.add(patient);
				}
			}
			redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
			if (doctorPatientsList.size() != 0) {
				redirectAttributes.addFlashAttribute("patientDiagnosis", doctorPatientsList.get(0).getPatientDiagnosis());
				redirectAttributes.addFlashAttribute("patientMedicines", doctorPatientsList.get(0).getPatientMedicines());
				if (doctorPatientsList.get(0).getPatientSickdays() > 0) {
					redirectAttributes.addFlashAttribute("patientSickdays", doctorPatientsList.get(0).getPatientSickdays());
				}else {
					redirectAttributes.addFlashAttribute("patientSickdays", "");
				}
				if (doctorPatientsList.get(0).getPatientOldDiagnoses() != null) {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", doctorPatientsList.get(0).getPatientOldDiagnoses());
				}else {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				}
				if (doctorPatientsList.get(0).getPatientOldMedicines() != null) {
					redirectAttributes.addFlashAttribute("patientOldMedicines", doctorPatientsList.get(0).getPatientOldMedicines());
				}else {
					redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				}
				if (doctorPatientsList.get(0).getPatientOldSickDays() > 0) {
					redirectAttributes.addFlashAttribute("patientOldSickdays", doctorPatientsList.get(0).getPatientOldSickDays());
				}else {
					redirectAttributes.addFlashAttribute("patientOldSickdays", "");
				}
			}else {
				redirectAttributes.addFlashAttribute("patientDiagnosis", "");
				redirectAttributes.addFlashAttribute("patientMedicines", "");
				redirectAttributes.addFlashAttribute("patientSickdays", "");
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				redirectAttributes.addFlashAttribute("patientOldSickdays", "");
			}
			return new RedirectView("/doctor_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "The identification number is incorrect!");
			return new RedirectView("/doctor_login");
		}
	}
	
	@GetMapping(value = "/doctor_page")
	public ModelAndView doctorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("doctor/doctor_page");
		return model;
	}
	
	@PostMapping(value = "/update_doctor_info")
	public RedirectView changeDoctorInfo(@ModelAttribute Doctor doctor, HttpServletResponse response, @CookieValue(value = "loggedInDoctorId", defaultValue = "") String loggedInDoctorId, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = null;
		if (loggedInDoctorId != "") {
			doctorExists = doctorService.getDoctorByIdentificationNumber(loggedInDoctorId);
		}
		String changedFields = "Changed fields: ";
		boolean somethingChanged = false;
		if (doctorExists != null) {
			String doctorName = doctorExists.getDoctorName();
			// Edit name
			if ((doctor.getDoctorName() != null) && (doctor.getDoctorName() != "")) {
				if (!doctorExists.getDoctorName().equalsIgnoreCase(doctor.getDoctorName())) {
					doctorExists.setDoctorName(doctor.getDoctorName());
					doctorName = doctorExists.getDoctorName();
					changedFields += "Name | ";
					somethingChanged = true;
				}
			}
			// Edit identification number
			if ((doctor.getDoctorIDNumber() != null) && (doctor.getDoctorIDNumber() != "")) {
				if (!doctorExists.getDoctorIDNumber().equalsIgnoreCase(doctor.getDoctorIDNumber())) {
					doctorExists.setDoctorIDNumber(doctor.getDoctorIDNumber());
					Cookie cookie = new Cookie("loggedInDoctorId", doctor.getDoctorIDNumber());
					response.addCookie(cookie);
					changedFields += "ID | ";
					somethingChanged = true;
				}
			}
			// Edit specialty
			if ((doctor.getDoctorSpecialty() != null) && (doctor.getDoctorSpecialty() != "")) {
				if (!doctorExists.getDoctorSpecialty().equalsIgnoreCase(doctor.getDoctorSpecialty())) {
					doctorExists.setDoctorSpecialty(doctor.getDoctorSpecialty());
					doctorName = doctorExists.getDoctorName();
					changedFields += "Specialty | ";
					somethingChanged = true;
				}
			}
			if (somethingChanged == true) {
				doctorService.createDoctor(doctorExists);
			}
			if (changedFields != "Changed fields: ") {
				redirectAttributes.addFlashAttribute("infoChangedMsg", changedFields);
			}
			redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
			redirectAttributes.addFlashAttribute("doctor", new Doctor());
			Iterable<Patient> patientsList = patientService.getAllPatients();
			List<Patient> doctorPatientsList = new ArrayList<Patient>();
			for (Patient patient : patientsList) {
				if (patient.getDoctorId() == doctorExists.getDoctorId()) {
					doctorPatientsList.add(patient);
				}
			}
			redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
			if (doctorPatientsList.size() != 0) {
				redirectAttributes.addFlashAttribute("patientDiagnosis", doctorPatientsList.get(0).getPatientDiagnosis());
				redirectAttributes.addFlashAttribute("patientMedicines", doctorPatientsList.get(0).getPatientMedicines());
				if (doctorPatientsList.get(0).getPatientSickdays() > 0) {
					redirectAttributes.addFlashAttribute("patientSickdays", doctorPatientsList.get(0).getPatientSickdays());
				}else {
					redirectAttributes.addFlashAttribute("patientSickdays", "");
				}
				if (doctorPatientsList.get(0).getPatientOldDiagnoses() != null) {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", doctorPatientsList.get(0).getPatientOldDiagnoses());
				}else {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				}
				if (doctorPatientsList.get(0).getPatientOldMedicines() != null) {
					redirectAttributes.addFlashAttribute("patientOldMedicines", doctorPatientsList.get(0).getPatientOldMedicines());
				}else {
					redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				}
				if (doctorPatientsList.get(0).getPatientOldSickDays() > 0) {
					redirectAttributes.addFlashAttribute("patientOldSickdays", doctorPatientsList.get(0).getPatientOldSickDays());
				}else {
					redirectAttributes.addFlashAttribute("patientOldSickdays", "");
				}
			}else {
				redirectAttributes.addFlashAttribute("patientDiagnosis", "");
				redirectAttributes.addFlashAttribute("patientMedicines", "");
				redirectAttributes.addFlashAttribute("patientSickdays", "");
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				redirectAttributes.addFlashAttribute("patientOldSickdays", "");
			}
			return new RedirectView("/doctor_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "You are not logged in!");
			return new RedirectView("/doctor_login");
		}
	}
	
	@GetMapping(value = "/delete_doctor")
	public RedirectView deleteDoctor(@CookieValue(value = "loggedInDoctorId", defaultValue = "") String loggedInDoctorId, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = null;
		if (loggedInDoctorId != "") {
			doctorExists = doctorService.getDoctorByIdentificationNumber(loggedInDoctorId);
		}
		if (doctorExists != null) {
			String doctorName = doctorExists.getDoctorName();
			Iterable<Patient> patientsList = patientService.getAllPatients();
			boolean doctorTaken = false;
			for	(Patient patient : patientsList) {
				if (patient.getDoctorId() == doctorExists.getDoctorId()) {
					doctorTaken = true;
					break;
				}
			}
			if (doctorTaken != true) {
				doctorService.deleteDoctorById(doctorExists.getDoctorId());
			}else {
				redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
				redirectAttributes.addFlashAttribute("doctor", new Doctor());
				redirectAttributes.addFlashAttribute("infoDeleteMsg", "You have active patients so you cannot delete your account!");
				List<Patient> doctorPatientsList = new ArrayList<Patient>();
				for (Patient patient : patientsList) {
					if (patient.getDoctorId() == doctorExists.getDoctorId()) {
						doctorPatientsList.add(patient);
					}
				}
				redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
				if (doctorPatientsList.size() != 0) {
					redirectAttributes.addFlashAttribute("patientDiagnosis", doctorPatientsList.get(0).getPatientDiagnosis());
					redirectAttributes.addFlashAttribute("patientMedicines", doctorPatientsList.get(0).getPatientMedicines());
					if (doctorPatientsList.get(0).getPatientSickdays() > 0) {
						redirectAttributes.addFlashAttribute("patientSickdays", doctorPatientsList.get(0).getPatientSickdays());
					}else {
						redirectAttributes.addFlashAttribute("patientSickdays", "");
					}
					if (doctorPatientsList.get(0).getPatientOldDiagnoses() != null) {
						redirectAttributes.addFlashAttribute("patientOldDiagnoses", doctorPatientsList.get(0).getPatientOldDiagnoses());
					}else {
						redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
					}
					if (doctorPatientsList.get(0).getPatientOldMedicines() != null) {
						redirectAttributes.addFlashAttribute("patientOldMedicines", doctorPatientsList.get(0).getPatientOldMedicines());
					}else {
						redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
					}
					if (doctorPatientsList.get(0).getPatientOldSickDays() > 0) {
						redirectAttributes.addFlashAttribute("patientOldSickdays", doctorPatientsList.get(0).getPatientOldSickDays());
					}else {
						redirectAttributes.addFlashAttribute("patientOldSickdays", "");
					}
				}
				return new RedirectView("/doctor_page");
			}
		}
		return new RedirectView("/doctor_login");
	}
	
	@PostMapping(value = "/set_patient_medical_info")
	public RedirectView setPatientMedicalInfo(@CookieValue(value = "loggedInDoctorId", defaultValue = "") String loggedInDoctorId, @RequestParam(name = "doctorPatientsList", required = false) String chosenPatient, @RequestParam("patientDiagnosis") String patientDiagnosis, @RequestParam("patientMedicines") String patientMedicines, @RequestParam("patientSickdays") String patientSickdays, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = null;
		if (loggedInDoctorId != "") {
			doctorExists = doctorService.getDoctorByIdentificationNumber(loggedInDoctorId);
		}
		if (doctorExists != null) {
			if (chosenPatient != null) {
				int chosenPatientId = Integer.parseInt(chosenPatient);
				Patient patientExists = patientService.getPatientById(chosenPatientId);
				boolean somethingChanged = false;
				if (patientExists != null) {
					if (patientExists.isPatientInsured() == true) {
						// Handle patient diagnosis
						if (patientDiagnosis != null) {
							if (!patientDiagnosis.isEmpty()) {
								if (patientExists.getPatientDiagnosis() == null) {
									patientExists.setPatientDiagnosis(patientDiagnosis);
									somethingChanged = true;
								}else {
									if (!patientExists.getPatientDiagnosis().equals(patientDiagnosis)) {
										patientExists.removePatientDiagnosis();
										patientExists.setPatientDiagnosis(patientDiagnosis);
										somethingChanged = true;
									}
								}
							}else {
								if (patientExists.getPatientDiagnosis() != null) {
									if (!patientExists.getPatientDiagnosis().equals("")) {
										patientExists.removePatientDiagnosis();
										somethingChanged = true;
									}
								}
							}
						}else {
							if (patientExists.getPatientDiagnosis() != null) {
								if (!patientExists.getPatientDiagnosis().equals("")) {
									patientExists.removePatientDiagnosis();
									somethingChanged = true;
								}
							}
						}
						// Handle patient medicines
						if (patientMedicines != null) {
							if (!patientMedicines.isEmpty()) {
								if (patientExists.getPatientMedicines() == null) {
									patientExists.setPatientMedicines(patientMedicines);
									somethingChanged = true;
								}else {
									if (!patientExists.getPatientMedicines().equals(patientMedicines)) {
										patientExists.removePatientMedicines();
										patientExists.setPatientMedicines(patientMedicines);
										somethingChanged = true;
									}
								}
							}else {
								if (patientExists.getPatientMedicines() != null) {
									if (!patientExists.getPatientMedicines().equals("")) {
										patientExists.removePatientMedicines();
										somethingChanged = true;
									}
								}
							}
						}else {
							if (patientExists.getPatientMedicines() != null) {
								if (!patientExists.getPatientMedicines().equals("")) {
									patientExists.removePatientMedicines();
									somethingChanged = true;
								}
							}
						}
						// Handle patient sick days
						if (patientSickdays != null) {
							if (!patientSickdays.isEmpty()) {
								int patientSickdaysNumVal = Integer.parseInt(patientSickdays);
								if (patientExists.getPatientSickdays() != patientSickdaysNumVal) {
									patientExists.setPatientSickdays(patientSickdaysNumVal);
									somethingChanged = true;
								}
							}else {
								if (patientExists.getPatientSickdays() != 0) {
									patientExists.setPatientSickdays(0);
									somethingChanged = true;
								}
							}
						}else {
							if (patientExists.getPatientSickdays() != 0) {
								patientExists.setPatientSickdays(0);
								somethingChanged = true;
							}
						}
						if (somethingChanged == true) {
							patientService.createPatient(patientExists);
							redirectAttributes.addFlashAttribute("patientMedicalInfoChangedMsg", "Patient " + patientExists.getPatientName() + "'s medical information updated!");
						}
					}else {
						redirectAttributes.addFlashAttribute("patientMedicalInfoChangedMsg", "Patient " + patientExists.getPatientName() + " is not insured so you cannot change their medical information!");
					}
				}
				String doctorName = doctorExists.getDoctorName();
				redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
				redirectAttributes.addFlashAttribute("doctor", new Doctor());
				Iterable<Patient> patientsList = patientService.getAllPatients();
				List<Patient> doctorPatientsList = new ArrayList<Patient>();
				for (Patient patient : patientsList) {
					if (patient.getDoctorId() == doctorExists.getDoctorId()) {
						doctorPatientsList.add(patient);
					}
				}
				redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
				if (doctorPatientsList.size() != 0) {
					redirectAttributes.addFlashAttribute("patientDiagnosis", doctorPatientsList.get(0).getPatientDiagnosis());
					redirectAttributes.addFlashAttribute("patientMedicines", doctorPatientsList.get(0).getPatientMedicines());
					if (doctorPatientsList.get(0).getPatientSickdays() > 0) {
						redirectAttributes.addFlashAttribute("patientSickdays", doctorPatientsList.get(0).getPatientSickdays());
					}else {
						redirectAttributes.addFlashAttribute("patientSickdays", "");
					}
					if (doctorPatientsList.get(0).getPatientOldDiagnoses() != null) {
						redirectAttributes.addFlashAttribute("patientOldDiagnoses", doctorPatientsList.get(0).getPatientOldDiagnoses());
					}else {
						redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
					}
					if (doctorPatientsList.get(0).getPatientOldMedicines() != null) {
						redirectAttributes.addFlashAttribute("patientOldMedicines", doctorPatientsList.get(0).getPatientOldMedicines());
					}else {
						redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
					}
					if (doctorPatientsList.get(0).getPatientOldSickDays() > 0) {
						redirectAttributes.addFlashAttribute("patientOldSickdays", doctorPatientsList.get(0).getPatientOldSickDays());
					}else {
						redirectAttributes.addFlashAttribute("patientOldSickdays", "");
					}
				}
				return new RedirectView("/doctor_page");
			}else {
				redirectAttributes.addFlashAttribute("patientMedicalInfoChangedMsg", "You must select a patient from the dropdown list!");
				String doctorName = doctorExists.getDoctorName();
				redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
				redirectAttributes.addFlashAttribute("doctor", new Doctor());
				Iterable<Patient> patientsList = patientService.getAllPatients();
				List<Patient> doctorPatientsList = new ArrayList<Patient>();
				for (Patient patient : patientsList) {
					if (patient.getDoctorId() == doctorExists.getDoctorId()) {
						doctorPatientsList.add(patient);
					}
				}
				redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
				redirectAttributes.addFlashAttribute("patientDiagnosis", "");
				redirectAttributes.addFlashAttribute("patientMedicines", "");
				redirectAttributes.addFlashAttribute("patientSickdays", "");
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				redirectAttributes.addFlashAttribute("patientOldSickdays", "");
				return new RedirectView("/doctor_page");
			}
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "You are not logged in!");
			return new RedirectView("/doctor_login");
		}
	}
	
	@GetMapping(value = "/populate_patient_info/{patientId}")
	public RedirectView populatePatientInfo(@PathVariable String patientId, @CookieValue(value = "loggedInDoctorId", defaultValue = "") String loggedInDoctorId, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = null;
		if (loggedInDoctorId != "") {
			doctorExists = doctorService.getDoctorByIdentificationNumber(loggedInDoctorId);
		}
		if (doctorExists != null) {
			String doctorName = doctorExists.getDoctorName();
			redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
			redirectAttributes.addFlashAttribute("doctor", new Doctor());
			Iterable<Patient> patientsList = patientService.getAllPatients();
			List<Patient> doctorPatientsList = new ArrayList<Patient>();
			for (Patient patient : patientsList) {
				if (patient.getDoctorId() == doctorExists.getDoctorId()) {
					doctorPatientsList.add(patient);
				}
			}
			redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
			int patientIdNumVal = Integer.parseInt(patientId);
			Patient patientExists = patientService.getPatientById(patientIdNumVal);
			if (patientExists != null) {
				redirectAttributes.addFlashAttribute("patientDiagnosis", patientExists.getPatientDiagnosis());
				redirectAttributes.addFlashAttribute("patientMedicines", patientExists.getPatientMedicines());
				if (patientExists.getPatientSickdays() > 0) {
					redirectAttributes.addFlashAttribute("patientSickdays", patientExists.getPatientSickdays());
				}else {
					redirectAttributes.addFlashAttribute("patientSickdays", "");
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
					redirectAttributes.addFlashAttribute("patientOldSickdays", patientExists.getPatientOldSickDays());
				}else {
					redirectAttributes.addFlashAttribute("patientOldSickdays", "");
				}
			}
			redirectAttributes.addFlashAttribute("patientId", patientIdNumVal);
			return new RedirectView("/doctor_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "You are not logged in!");
			return new RedirectView("/doctor_login");
		}
	}
	
	@PostMapping(value = "/get_patients_by_diagnosis")
	public RedirectView getPatientsByDiagnosis(@CookieValue(value = "loggedInDoctorId", defaultValue = "") String loggedInDoctorId, @RequestParam("patientDiagnosisSearchInput") String patientDiagnosisSearchInput, RedirectAttributes redirectAttributes) {
		Doctor doctorExists = null;
		if (loggedInDoctorId != "") {
			doctorExists = doctorService.getDoctorByIdentificationNumber(loggedInDoctorId);
		}
		if (doctorExists != null) {
			String doctorName = doctorExists.getDoctorName();
			redirectAttributes.addFlashAttribute("loggedInDoctorName", doctorName);
			redirectAttributes.addFlashAttribute("doctor", new Doctor());
			Iterable<Patient> patientsList = patientService.getAllPatients();
			List<Patient> doctorPatientsList = new ArrayList<Patient>();
			for (Patient patient : patientsList) {
				if (patient.getDoctorId() == doctorExists.getDoctorId()) {
					doctorPatientsList.add(patient);
				}
			}
			redirectAttributes.addFlashAttribute("doctorPatientsList", doctorPatientsList);
			if (doctorPatientsList.size() != 0) {
				redirectAttributes.addFlashAttribute("patientDiagnosis", doctorPatientsList.get(0).getPatientDiagnosis());
				redirectAttributes.addFlashAttribute("patientMedicines", doctorPatientsList.get(0).getPatientMedicines());
				if (doctorPatientsList.get(0).getPatientSickdays() > 0) {
					redirectAttributes.addFlashAttribute("patientSickdays", doctorPatientsList.get(0).getPatientSickdays());
				}else {
					redirectAttributes.addFlashAttribute("patientSickdays", "");
				}
				if (doctorPatientsList.get(0).getPatientOldDiagnoses() != null) {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", doctorPatientsList.get(0).getPatientOldDiagnoses());
				}else {
					redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				}
				if (doctorPatientsList.get(0).getPatientOldMedicines() != null) {
					redirectAttributes.addFlashAttribute("patientOldMedicines", doctorPatientsList.get(0).getPatientOldMedicines());
				}else {
					redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				}
				if (doctorPatientsList.get(0).getPatientOldSickDays() > 0) {
					redirectAttributes.addFlashAttribute("patientOldSickdays", doctorPatientsList.get(0).getPatientOldSickDays());
				}else {
					redirectAttributes.addFlashAttribute("patientOldSickdays", "");
				}
			}else {
				redirectAttributes.addFlashAttribute("patientDiagnosis", "");
				redirectAttributes.addFlashAttribute("patientMedicines", "");
				redirectAttributes.addFlashAttribute("patientSickdays", "");
				redirectAttributes.addFlashAttribute("patientOldDiagnoses", "Old diagnoses");
				redirectAttributes.addFlashAttribute("patientOldMedicines", "Old medicines");
				redirectAttributes.addFlashAttribute("patientOldSickdays", "");
			}
			if (patientDiagnosisSearchInput != "") {
				boolean patientsWithDiagnosisFound = false;
				// Search through all patients, not just the ones subscribed to you.
				// Iterable<Patient> patientsWithDiagnosis = doctorService.getPatientsByDiagnosis(patientDiagnosisSearchInput);
				// Search only through the patients who have subscribed to you.
				List<Patient> patientsWithDiagnosis = new ArrayList<Patient>();
				for (Patient patient : doctorPatientsList) {
					if (patient.getPatientDiagnosis() != null) {
						if (patient.getPatientDiagnosis().equalsIgnoreCase(patientDiagnosisSearchInput)) {
							patientsWithDiagnosis.add(patient);
							if (patientsWithDiagnosisFound != true) {
								patientsWithDiagnosisFound = true;
							}
						}
					}
				}
				redirectAttributes.addFlashAttribute("patientsWithDiagnosis", patientsWithDiagnosis);
				if (patientsWithDiagnosisFound == true) {
					redirectAttributes.addFlashAttribute("patientDiagnosisSearchInput", patientDiagnosisSearchInput);
				}
				String patientDiagnosisSearchMsg = "";
				if (patientsWithDiagnosis.size() == 0) {
					patientDiagnosisSearchMsg = "There are no patients with diagnosis " + patientDiagnosisSearchInput;
				}
				if (patientsWithDiagnosis.size() == 1) {
					patientDiagnosisSearchMsg = "There is 1 patient with diagnosis " + patientDiagnosisSearchInput;
				}
				if (patientsWithDiagnosis.size() > 1) {
					patientDiagnosisSearchMsg = "There are " + patientsWithDiagnosis.size() + " patients with diagnosis " + patientDiagnosisSearchInput;
				}
				redirectAttributes.addFlashAttribute("patientDiagnosisSearchMsg", patientDiagnosisSearchMsg);
			}
			return new RedirectView("/doctor_page");
		}else {
			redirectAttributes.addFlashAttribute("actionMsg", "You are not logged in!");
			return new RedirectView("/doctor_login");
		}
	}
}