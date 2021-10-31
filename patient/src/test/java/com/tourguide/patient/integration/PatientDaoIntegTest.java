package com.tourguide.patient.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tourguide.patient.dao.PatientDao;
import com.tourguide.patient.domain.Patient;

@SpringBootTest
public class PatientDaoIntegTest {

	@Autowired
	private PatientDao patientDao;

	@Test
	public void findByFirstNameAndLastNameTest() {
		Integer id = 1;
		String firstName = "Test";
		String lastName = "TestNone";
		Patient patient = patientDao.findById(id).get();
		assertEquals(firstName, patient.getFirstName());
		assertEquals(lastName, patient.getLastName());
	}

	@Test
	public void findaAllTest() {
		List<Patient> patient = StreamSupport.stream(patientDao.findAll().spliterator(), false)
				.collect(Collectors.toList());
		assertEquals(4, patient.size());
	}

}
