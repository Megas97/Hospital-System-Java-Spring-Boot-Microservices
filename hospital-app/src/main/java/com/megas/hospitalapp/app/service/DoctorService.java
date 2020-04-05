package com.megas.hospitalapp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.megas.hospitalapp.app.dto.DoctorsDTO;
import com.megas.hospitalapp.app.dto.PatientsDTO;
import com.megas.hospitalapp.app.models.Doctor;
import com.megas.hospitalapp.app.models.Patient;

@Service
public class DoctorService {
	@Autowired
    RestTemplate restTemplate;
	
	public Doctor createDoctor(Doctor doctor) {
		return restTemplate.postForObject("http://doctor-service/doctors/create", doctor, Doctor.class);
	}
	
	public Iterable<Doctor> getAllDoctors() {
        DoctorsDTO doctorsDTO = restTemplate.getForObject("http://doctor-service/doctors/get/all", DoctorsDTO.class);
        return doctorsDTO.getDoctors();
    }
	
	public Doctor getDoctorById(int id) {
		return restTemplate.getForObject("http://doctor-service/doctors/get-by-id/" + id, Doctor.class);
	}
	
	public Doctor getDoctorByIdentificationNumber(String idnumber) {
		return restTemplate.getForObject("http://doctor-service/doctors/get-by-id-number/" + idnumber, Doctor.class);
	}
	
	public void deleteDoctorById(int id) {
		String url = "http://doctor-service/doctors/delete/{id}";
		restTemplate.delete(url, id);
	}
	
	public Iterable<Patient> getPatientsByDiagnosis(String diagnosis){
		PatientsDTO patientsDTO = restTemplate.getForObject("http://patient-service/patients/get-patients-with-diagnosis/" + diagnosis, PatientsDTO.class);
		return patientsDTO.getPatients();
	}
}