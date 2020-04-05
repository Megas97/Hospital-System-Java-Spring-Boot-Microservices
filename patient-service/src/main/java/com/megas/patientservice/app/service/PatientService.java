package com.megas.patientservice.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megas.patientservice.app.dao.PatientDao;
import com.megas.patientservice.app.models.Patient;

@Service
public class PatientService {
	@Autowired
	private PatientDao patientDao;
	
						// General Patient Functions //
	
	public Patient createPatient(Patient patient) {
		return patientDao.save(patient);
	}

	public Patient getPatientById(Integer patientId) {
		return patientDao.findById(patientId).orElse(null);
	}
	
	public Patient getPatientByIdNumber(String patientIdNumber) {
		return patientDao.findByPatientIDNumber(patientIdNumber);
	}

	public Iterable<Patient> getAllPatients() {
		return patientDao.findAll();
	}

	public void deletePatient(Integer patientId) {
		patientDao.deleteById(patientId);
	}
	
					// Update Patient Information Functions //
	
	public Patient updatePatientName(Integer patientId, String newName) {
		Patient patientFromDb = patientDao.findById(patientId).orElse(null);
		if (patientFromDb == null) return null;
		patientFromDb.setPatientName(newName);
		Patient updatedPatient = patientDao.save(patientFromDb);
		return updatedPatient;
	}
	
	public Patient updatePatientIDNumber(Integer patientId, String newIDNumber) {
		Patient patientFromDb = patientDao.findById(patientId).orElse(null);
		if (patientFromDb == null) return null;
		patientFromDb.setPatientIDNumber(newIDNumber);
		Patient updatedPatient = patientDao.save(patientFromDb);
		return updatedPatient;
	}
	
	public Patient updatePatientInsuredStatus(Integer patientId, boolean newinsuredStatus) {
		Patient patientFromDb = patientDao.findById(patientId).orElse(null);
		if (patientFromDb == null) return null;
		patientFromDb.setPatientInsured(newinsuredStatus);
		Patient updatedPatient = patientDao.save(patientFromDb);
		return updatedPatient;
	}
	
				// Get All Patients With Same Diagnosis Function //
	
	public List<Patient> getAllPatientsWithSameDiagnosis(String diagnosis) {
		Iterable<Patient> allPatients = patientDao.findAll();
		List<Patient> patientsWithSameDiagnosis = new ArrayList<Patient>();
		for(Patient patient : allPatients) {
			if (patient != null) {
				if (patient.getPatientDiagnosis().equalsIgnoreCase(diagnosis)) {
					patientsWithSameDiagnosis.add(patient);
				}
			}
		}
		return patientsWithSameDiagnosis;
	}
}