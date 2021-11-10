package com.mediscreen.patient.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Sex;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
	
	@Test 
	public void cruTest() {
		Patient patient = new Patient(null, "Paul", "Pal", "1900-12-25", Sex.MALE.getFormat());
		Patient patientSave =  patientDao.save(patient);
		Patient patientGet = patientDao.findById(patientSave.getId()).get();
		assertThat(patientSave).usingRecursiveComparison().isEqualTo(patientGet);
		
		patient =  new Patient(patientSave.getId(), "PaulPaul", "PalPal", "2000-12-25", Sex.FEMALE.getFormat());
		Patient patientUpdate =  patientDao.save(patient);
		patientGet = patientDao.findById(patient.getId()).get();
		assertThat(patientUpdate).usingRecursiveComparison().isEqualTo(patientGet);
		
	}

}
