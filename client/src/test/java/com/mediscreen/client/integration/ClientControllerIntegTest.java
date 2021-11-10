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
		mockMvc.perform(get("/patient/list"))
		.andExpect(model().attributeExists("patients"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showUpdatePatientTest() throws Exception {
		mockMvc.perform(get("/patient/update/{0}",1))
		.andExpect(model().attribute("title","Edit patient"))
		.andExpect(model().attribute("titleForm","Edit patient"))
		.andExpect(model().attribute("titleButton","Edit"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showAddPatientTest() throws Exception {
		mockMvc.perform(get("/patient/add"))
		.andExpect(model().attribute("title","Add patient"))
		.andExpect(model().attribute("titleForm","Add patient"))
		.andExpect(model().attribute("titleButton","Add"))
		.andExpect(status().isOk());
	}
}

