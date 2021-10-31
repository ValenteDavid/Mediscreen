package com.mediscreen.patient.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Patient
 * @author David
 *
 */
@Entity
@Table(name = "Patient")
public class Patient {

	/**
	 * Patient id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private Integer id;
	
	/**
	 * Patient first name
	 */
	@Column(name = "first_name", nullable = false)
	@NotNull(message = "First name cannot be empty")
	private String firstName;
	
	/**
	 * Patien last name
	 */
	@Column(name = "last_name",nullable = false)
	@NotNull(message = "Last name cannot be empty")
	private String lastName;
	
	/**
	 * Patient date of birth
	 */
	@Column(nullable = false)
	@NotNull(message = "Date of birth cannot be empty")
	private String dob;
	
	/**
	 * Patient sex
	 * @see Sex
	 */
	@Column(nullable = false)
	@NotNull(message = "Sex cannot be empty")
	private String sex;
	
	/**
	 * Patient address
	 */
	private String address;
	
	/**
	 * Patient phone
	 */
	private String phone;
	
	public Patient() {
	}

	public Patient(Integer id,String firstName, String lastName, String dob, String sex) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.sex = sex;
	}

	public Patient(Integer id, String firstName,String lastName, String dob, String sex, String address, String phone) {
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
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", sex="
				+ sex + ", address=" + address + ", phone=" + phone + "]";
	}
	
}
