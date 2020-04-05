package com.megas.patientservice.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patient")
public class Patient {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="patient_id")
	private Integer patientId;
	
	@Column(name="patient_name")
	private String patientName;
	
	@Column(name="patient_idnumber")
	private String patientIDNumber;
	
	@Column(name="patient_insured")
	private boolean patientInsured = false;
	
	@Column(name="patient_diagnosis")
	private String patientDiagnosis;
	
	@Column(name="patient_medicines")
	private String patientMedicines;

	@Column(name="patient_sickdays")
	private Integer patientSickdays = 0;

	@Column(name="doctor_id")
	private int doctorId;
	
	@Column(name="patient_old_diagnoses")
	private String patientOldDiagnoses;
	
	@Column(name="patient_old_medicines")
	private String patientOldMedicines;
	
	@Column(name="patient_old_sickdays")
	private Integer patientOldSickDays = 0;
	
	public Patient() {
		
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientIDNumber() {
		return patientIDNumber;
	}

	public void setPatientIDNumber(String patientIDNumber) {
		this.patientIDNumber = patientIDNumber;
	}

	public boolean isPatientInsured() {
		return patientInsured;
	}

	public void setPatientInsured(boolean patientInsured) {
		this.patientInsured = patientInsured;
	}
	
	public void removePatientDiagnosis() {
		if (this.patientOldDiagnoses != null && this.patientOldDiagnoses != "") {
			this.patientOldDiagnoses += ", " + this.patientDiagnosis;
		}else {
			this.patientOldDiagnoses = this.patientDiagnosis;
		}
		this.patientDiagnosis = null;
	}
	
	public String getPatientDiagnosis() {
		return patientDiagnosis;
	}

	public void setPatientDiagnosis(String patientDiagnosis) {
		this.patientDiagnosis = patientDiagnosis;
	}
	
	public String getPatientMedicines() {
		return patientMedicines;
	}

	public void setPatientMedicines(String patientMedicines) {
		this.patientMedicines = patientMedicines;
	}
	
	public void addPatientMedicine(String patientMedicine) {
		if (this.patientMedicines != null && this.patientMedicines != "") {
			this.patientMedicines += ", " + patientMedicine;
		}else {
			this.patientMedicines = patientMedicine;
		}
	}
	
	public void removePatientMedicines() {
		if (this.patientOldMedicines != null && this.patientOldMedicines != "") {
			this.patientOldMedicines += ", " + this.patientMedicines;
		}else {
			this.patientOldMedicines = this.patientMedicines;
		}
		this.patientMedicines = null;
	}
	
	public Integer getPatientSickdays() {
		return patientSickdays;
	}

	public void setPatientSickdays(Integer patientSickdays) {
		if (patientSickdays == 0) {
			if (this.patientOldSickDays != null && this.patientOldSickDays != 0) {
				this.patientOldSickDays += this.patientSickdays;
			}else {
				this.patientOldSickDays = this.patientSickdays;
			}
		}
		this.patientSickdays = patientSickdays;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientOldDiagnoses() {
		return patientOldDiagnoses;
	}

	public void setPatientOldDiagnoses(String patientOldDiagnoses) {
		this.patientOldDiagnoses = patientOldDiagnoses;
	}

	public String getPatientOldMedicines() {
		return patientOldMedicines;
	}

	public void setPatientOldMedicines(String patientOldMedicines) {
		this.patientOldMedicines = patientOldMedicines;
	}

	public Integer getPatientOldSickDays() {
		return patientOldSickDays;
	}

	public void setPatientOldSickDays(Integer patientOldSickDays) {
		this.patientOldSickDays = patientOldSickDays;
	}
}