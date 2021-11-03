package com.mediscreen.client.controller.dto;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * Patient dto
 * @author David
 *
 */
public class PatientDto {

	@NumberFormat
	private Integer id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@DateTimeFormat
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

	@Override
	public String toString() {
		return "PatientDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", sex=" + sex + ", address=" + address + ", phone=" + phone + "]";
	}
	
}
