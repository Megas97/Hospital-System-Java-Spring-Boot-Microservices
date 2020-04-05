package com.megas.doctorservice.app.dao;

import org.springframework.data.repository.CrudRepository;
import com.megas.doctorservice.app.models.Doctor;

public interface DoctorDao extends CrudRepository<Doctor, Integer> {
	Doctor findByDoctorIDNumber(String idnumber);
}