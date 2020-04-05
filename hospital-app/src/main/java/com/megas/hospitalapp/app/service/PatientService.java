package com.megas.hospitalapp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.megas.hospitalapp.app.dto.PatientsDTO;
import com.megas.hospitalapp.app.models.Patient;

@Service
public class PatientService {
	@Autowired
    RestTemplate restTemplate;
	
	public Patient createPatient(Patient patient) {
		return restTemplate.postForObject("http://patient-service/patients/create", patient, Patient.class);
	}
	
	public Iterable<Patient> getAllPatients() {
		PatientsDTO patientsDTO = restTemplate.getForObject("http://patient-service/patients/get/all", PatientsDTO.class);
        return patientsDTO.getPatients();
    }
	
	public Patient getPatientById(int id) {
		return restTemplate.getForObject("http://patient-service/patients/get-by-id/" + id, Patient.class);
	}
	
	public Patient getPatientByIdentificationNumber(String idnumber) {
		return restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + idnumber, Patient.class);
	}
	
	public void deletePatientById(int id) {
		String url = "http://patient-service/patients/delete/{id}";
		restTemplate.delete(url, id);
	}
}