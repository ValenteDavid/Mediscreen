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
public class HistoricControllerInteg {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void showHistoricListTest() throws Exception {
		Integer patientId = 1;
		mockMvc.perform(get("/historic/list/{0}", patientId))
				.andExpect(model().attributeExists("patientDto"))
				.andExpect(model().attributeExists("historics"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void showUpdateHistoricTest() throws Exception {
		mockMvc.perform(get("/historic/update/{0}/{1}",1,"61937e698bcc2a1d678ae0b6"))
		.andExpect(model().attribute("title","Edit historic"))
		.andExpect(model().attribute("titleButton","Edit"))
		.andExpect(model().attributeExists("patientDto"))
		.andExpect(model().attributeExists("historicDto"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showAddHistoricTest() throws Exception {
		mockMvc.perform(get("/historic/add/{0}",1))
		.andExpect(model().attribute("title","New historic"))
		.andExpect(model().attribute("titleButton","Add"))
		.andExpect(model().attributeExists("patientDto"))
		.andExpect(model().attributeExists("historicDto"))
		.andExpect(status().isOk());
	}
}
