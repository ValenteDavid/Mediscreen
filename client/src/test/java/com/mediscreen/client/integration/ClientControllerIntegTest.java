package com.mediscreen.client.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void showPatientListTest() throws Exception {
		mockMvc.perform(get("/patients"))
		.andExpect(model().attributeExists("patients"))
		.andExpect(status().isOk());
	}
}

