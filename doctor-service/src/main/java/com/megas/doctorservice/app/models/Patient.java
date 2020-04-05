package com.megas.doctorservice.app.models;

public class Patient {
	private Integer patientId;
	
	private String patientName;
	
	private String patientIDNumber;
	
	private boolean patientInsured = false;
	
	private String patientDiagnosis;
	
	private String patientMedicines;
	
	private Integer patientSickdays = 0;
	
	private int doctorId;
	
	private String patientOldDiagnoses;
	
	private String patientOldMedicines;
	
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