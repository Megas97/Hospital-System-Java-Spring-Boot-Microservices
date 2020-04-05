CREATE DATABASE `hospital_db`;

DROP TABLE IF EXISTS `patient`;
DROP TABLE IF EXISTS `doctor`;

CREATE TABLE IF NOT EXISTS `doctor` (
  `doctor_id` INT NOT NULL,
  `doctor_name` VARCHAR(250) NULL,
  `doctor_idnumber` VARCHAR(250) NULL,
  `doctor_specialty` VARCHAR(250) NULL,
  PRIMARY KEY (`doctor_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `patient` (
  `patient_id` INT NOT NULL,
  `patient_name` VARCHAR(250) NULL,
  `patient_idnumber` VARCHAR(250) NULL,
  `patient_insured` BIT(1) NULL,
  `patient_diagnosis` VARCHAR(250) NULL,
  `patient_medicines` VARCHAR(250) NULL,
  `patient_sickdays` INT NULL,
  `doctor_id` INT NULL,
  `patient_old_diagnoses` VARCHAR(250) NULL,
  `patient_old_medicines` VARCHAR(250) NULL,
  `patient_old_sickdays` INT NULL,
  PRIMARY KEY (`patient_id`),
  INDEX `doctor_id_idx` (`doctor_id` ASC),
  CONSTRAINT `doctor_id`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `doctor` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;