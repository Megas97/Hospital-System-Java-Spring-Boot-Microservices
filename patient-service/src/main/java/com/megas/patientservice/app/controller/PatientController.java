package com.megas.patientservice.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megas.patientservice.app.dto.PatientsDTO;
import com.megas.patientservice.app.models.Patient;
import com.megas.patientservice.app.service.PatientService;

@RestController
@RequestMapping(value="/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
						// General Patient Functions //
	
	@PostMapping(value="/create")
	public Patient createPatient(@RequestBody Patient patient) {
		return patientService.createPatient(patient);
	}
	
	@GetMapping(value="/get-by-id/{patientId}")
	public Patient getPatientById(@PathVariable("patientId") Integer patientId) {
		return patientService.getPatientById(patientId);
	}
	
	@GetMapping(value="/get-by-id-number/{patientIdNumber}")
	public Patient getPatientByIdNumber(@PathVariable("patientIdNumber") String patientIdNumber) {
		return patientService.getPatientByIdNumber(patientIdNumber);
	}
	
	@GetMapping(value="/get/all")
	public PatientsDTO getAllDoctors() {
		return new PatientsDTO(patientService.getAllPatients());
	}
	
	@DeleteMapping(value="/delete/{patientId}")
	public void deletePatient(@PathVariable("patientId") Integer patientId) {
		patientService.deletePatient(patientId);
	}
	
						// Patient Doctor Functions //
	
	@PutMapping(value="/{patientId}/set-doctor/{doctorId}")
	public Patient setPatientDoctor(@PathVariable("patientId") Integer patientId, @PathVariable("doctorId") Integer doctorId) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.setDoctorId(doctorId);
		return patientService.createPatient(patient);
	}
	
					// Update Patient Information Functions //
	
	@PutMapping(value="/{patientId}/update-name/{newName}")
	public Patient updatePatientName(@PathVariable("patientId") Integer patientId, @PathVariable("newName") String newName) {
		return patientService.updatePatientName(patientId, newName);
	}
	
	@PutMapping(value="/{patientId}/update-id-number/{newIDNumber}")
	public Patient updatePatientIDNumber(@PathVariable("patientId") Integer patientId, @PathVariable("newIDNumber") String newIDNumber) {
		return patientService.updatePatientIDNumber(patientId, newIDNumber);
	}
	
	@PutMapping(value="/{patientId}/update-insured-status/{newInsuredStatus}")
	public Patient updatePatientInsuredStatus(@PathVariable("patientId") Integer patientId, @PathVariable("newInsuredStatus") boolean newInsuredStatus) {
		return patientService.updatePatientInsuredStatus(patientId, newInsuredStatus);
	}
	
						// Patient Diagnosis Functions //

	@PutMapping(value="/{patientId}/set-patient-diagnosis/{newDiagnosis}")
	public Patient setPatientDiagnosis(@PathVariable("patientId") Integer patientId, @PathVariable("newDiagnosis") String newDiagnosis) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.setPatientDiagnosis(newDiagnosis);
		return patientService.createPatient(patient);
	}
	
	@GetMapping(value="/get-patients-with-diagnosis/{patientDiagnosis}")
	public Iterable<Patient> getPatientsWithSameDiagnosis(@PathVariable("patientDiagnosis") String patientDiagnosis) {
		return patientService.getAllPatientsWithSameDiagnosis(patientDiagnosis);
	}
	
	@PutMapping(value="/{patientId}/remove-patient-diagnosis")
	public Patient removePatientDiagnosis(@PathVariable("patientId") Integer patientId) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.removePatientDiagnosis();
		return patientService.createPatient(patient);
	}
	
						// Patient Sick Days Function //
	
	@PutMapping(value="/{patientId}/set-patient-sickdays/{patientSickDays}")
	public Patient setPatientSickDays(@PathVariable("patientId") Integer patientId, @PathVariable("patientSickDays") Integer patientSickDays) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.setPatientSickdays(patientSickDays);
		return patientService.createPatient(patient);
	}
	
						// Patient Medicine Functions //
	
	@PutMapping(value="/{patientId}/set-patient-medicine/{medicineName}")
	public Patient setPatientMedicine(@PathVariable("patientId") Integer patientId, @PathVariable("medicineName") String medicineName) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.addPatientMedicine(medicineName);
		return patientService.createPatient(patient);
	}
	
	@PutMapping(value="/{patientId}/remove-patient-medicines")
	public Patient removePatientMedicines(@PathVariable("patientId") Integer patientId) {
		Patient patient = patientService.getPatientById(patientId);
		if (patient == null) return null;
		patient.removePatientMedicines();
		return patientService.createPatient(patient);
	}
}