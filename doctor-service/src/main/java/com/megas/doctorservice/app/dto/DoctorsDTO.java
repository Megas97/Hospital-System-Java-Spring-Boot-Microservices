package com.megas.doctorservice.app.dto;

import com.megas.doctorservice.app.models.Doctor;

public class DoctorsDTO {
	private Iterable<Doctor> doctors;
	
	public DoctorsDTO () {
		
	}
	
	public DoctorsDTO (Iterable<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	public void setDoctors(Iterable<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	public Iterable<Doctor> getDoctors() {
		return this.doctors;
	}
}