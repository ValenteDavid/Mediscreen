package com.tourguide.patient.unit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tourguide.patient.dao.PatientDao;
import com.tourguide.patient.domain.Patient;
import com.tourguide.patient.domain.Sex;

@WebMvcTest
@AutoConfigureMockMvc
public class PatientControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientDao patientDao;

	@Test
	public void getPatientTest_isOk() throws Exception {
		Integer id = 1;
		String firstName = "Test";
		String lastName = "TestNone";

		when(patientDao.findById(id)).thenReturn(Optional.of(
				new Patient(id,firstName, lastName, "2021-01-01", Sex.MALE.getFormat())));

		mockMvc.perform(get("/patient/{0}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName))
				.andExpect(jsonPath("$.dob").value("2021-01-01"))
				.andExpect(jsonPath("$.sex").value(Sex.MALE.getFormat()))
				.andExpect(jsonPath("$.address").doesNotExist())
				.andExpect(jsonPath("$.phone").doesNotExist());
	}

	@Test
	public void getPatientAllTest_isOk() throws Exception {
		List<Patient> patientList = new ArrayList<>();
				patientList.add(new Patient(1,"Test", "TestNone", "2021-01-01", Sex.MALE.getFormat()));
		when(patientDao.findAll()).thenReturn(patientList);
		
		mockMvc.perform(get("/patients"))
		.andExpect(status().isOk())
	     .andExpect(jsonPath("$", hasSize(1)));
	}

}
