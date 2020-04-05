package com.megas.patientservice.app.dao;

import org.springframework.data.repository.CrudRepository;
import com.megas.patientservice.app.models.Patient;

public interface PatientDao extends CrudRepository<Patient, Integer> {
	Patient findByPatientIDNumber(String patientIDNumber);
}