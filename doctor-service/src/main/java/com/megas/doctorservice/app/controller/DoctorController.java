package com.megas.doctorservice.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megas.doctorservice.app.dto.DoctorsDTO;
import com.megas.doctorservice.app.models.Doctor;
import com.megas.doctorservice.app.service.DoctorService;

@RestController
@RequestMapping(value="/doctors")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
						// General Doctor Functions //
	
	@PostMapping(value="/create")
	public Doctor createDoctor(@RequestBody Doctor doctor) {
		return doctorService.createDoctor(doctor);
	}
	
	@GetMapping(value="/get-by-id/{doctorId}")
	public Doctor getDoctorById(@PathVariable("doctorId") Integer doctorId) {
		return doctorService.getDoctorById(doctorId);
	}
	
	@GetMapping(value="/get-by-id-number/{doctorIdNumber}")
	public Doctor getDoctorByIdentificationNumber(@PathVariable("doctorIdNumber") String doctorIdNumber) {
		return doctorService.getDoctorByIdNumber(doctorIdNumber);
	}
	
	@GetMapping(value="/get/all")
	public DoctorsDTO getAllDoctors() {
		return new DoctorsDTO(doctorService.getAllDoctors());
	}
	
	@DeleteMapping(value="/delete/{doctorId}")
	public void deleteDoctor(@PathVariable("doctorId") Integer doctorId) {
		doctorService.deleteDoctor(doctorId);
	}
	
					// Get All Doctor Patients Function //
	
	/*@GetMapping(value="/view/{doctorId}/get-all-patients")
	public Iterable<Patient> getAllDoctorPatients(@PathVariable("doctorId") Integer doctorId){
		return doctorService.getAllDoctorPatients(doctorId);
	}*/
	
					// Update Doctor Information Functions //
	
	@PutMapping(value="/{doctorId}/update-name/{newName}")
	public Doctor updateDoctorName(@PathVariable("doctorId") Integer doctorId, @PathVariable("newName") String newName) {
		return doctorService.updateDoctorName(doctorId, newName);
	}
	
	@PutMapping(value="/{doctorId}/update-specialty/{newSpecialty}")
	public Doctor updateDoctorSpecialty(@PathVariable("doctorId") Integer doctorId, @PathVariable("newSpecialty") String newSpecialty) {
		return doctorService.updateDoctorSpecialty(doctorId, newSpecialty);
	}
}