package com.tourguide.patient.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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

}
