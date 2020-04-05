package com.megas.hospitalapp.app.models;

public class Doctor {
	private Integer doctorId;
	
	private String doctorName;
	
	private String doctorIDNumber;
	
	private String doctorSpecialty;
	
	public Doctor() {
		
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorIDNumber() {
		return doctorIDNumber;
	}

	public void setDoctorIDNumber(String doctorIDNumber) {
		this.doctorIDNumber = doctorIDNumber;
	}

	public String getDoctorSpecialty() {
		return doctorSpecialty;
	}

	public void setDoctorSpecialty(String doctorSpecialty) {
		this.doctorSpecialty = doctorSpecialty;
	}
}