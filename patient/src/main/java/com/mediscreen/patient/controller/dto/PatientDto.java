package com.mediscreen.patient.controller.dto;

import javax.validation.constraints.NotNull;

import com.mediscreen.patient.domain.Patient;

/**
 * Patient dto
 * 
 * @author David
 *
 */
public class PatientDto {

	private Integer id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String dob;
	@NotNull
	private String sex;
	private String address;
	private String phone;

	public PatientDto() {
	}

	public PatientDto(Integer id, String firstName, String lastName, String dob, String sex, String address,
			String phone) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Convert to Dto
	 * 
	 * @param patient
	 * @return PatientDto from the patient
	 * @see Patient
	 */
	public static PatientDto convertToDto(Patient patient) {
		return new PatientDto(
				patient.getId(),
				patient.getFirstName(),
				patient.getLastName(),
				patient.getDob(),
				patient.getSex(),
				patient.getAddress(),
				patient.getPhone());
	}

	/*
	 * Convert to Domain
	 * 
	 * @param patient
	 * 
	 * @return PatientDto from the patient
	 * 
	 * @see Patient
	 */
	public static Patient convertToDomain(PatientDto patientDto) {
		return new Patient(
				patientDto.getId(),
				patientDto.getFirstName(),
				patientDto.getLastName(),
				patientDto.getDob(),
				patientDto.getSex(),
				patientDto.getAddress(),
				patientDto.getPhone());
	}

	@Override
	public String toString() {
		return "PatientDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", sex=" + sex + ", address=" + address + ", phone=" + phone + "]";
	}

}
