package com.mediscreen.patient.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.controller.dto.PatientDto;
import com.mediscreen.patient.domain.Patient;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerIntegTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getPatientTest_isOk() throws Exception {
		Integer id = 1;
		String firstName = "Test";
		String lastName = "TestNone";

		mockMvc.perform(get("/patient/{0}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName))
				.andExpect(jsonPath("$.dob").value("1966-12-31"))
				.andExpect(jsonPath("$.sex").value("F"))
				.andExpect(jsonPath("$.address").value("1 Brookside St"))
				.andExpect(jsonPath("$.phone").value("100-222-3333"));
	}

	@Test
	public void getPatientAllTest() throws Exception {
		mockMvc.perform(get("/patients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void updatePatientTest_isOk() throws Exception {
		Integer id = 2;

		Patient patient = new Patient(id, "FirsName", "LastName", "2021-01-01", "M", "", "");
		PatientDto patientDto = PatientDto.convertToDto(patient);

		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(put("/patient/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
