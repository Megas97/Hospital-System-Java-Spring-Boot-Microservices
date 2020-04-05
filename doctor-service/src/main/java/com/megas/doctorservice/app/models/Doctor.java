package com.megas.doctorservice.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doctor")
public class Doctor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="doctor_id")
	private Integer doctorId;
	
	@Column(name="doctor_name", nullable=false)
	private String doctorName;
	
	@Column(name="doctor_idnumber", nullable=false)
	private String doctorIDNumber;
	
	@Column(name="doctor_specialty", nullable=false)
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