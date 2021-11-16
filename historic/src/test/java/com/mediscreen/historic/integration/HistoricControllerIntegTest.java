package com.mediscreen.historic.integration;

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
import com.mediscreen.historic.controller.dto.HistoricDto;
import com.mediscreen.historic.domain.Historic;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoricControllerIntegTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getHistoricByIdTest_isOk() throws Exception {
		String id = "61937e698bcc2a1d678ae0b8";

		mockMvc.perform(get("/historic/{0}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.patientId").value(2))
				.andExpect(jsonPath("$.note").value("Note note note"));
	}

	@Test
	public void getHistoricByPatientIdTest() throws Exception {
		Integer patientId = 1;
		mockMvc.perform(get("/historic/list/{0}",patientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void updateHistoricTest_isOk() throws Exception {
		String id = "61937e698bcc2a1d678ae0b9";

		Historic historic = new Historic(id, 2, "NOTENOTENOTE");
		HistoricDto historictDto = HistoricDto.convertToDto(historic);

		String body = new ObjectMapper().writeValueAsString(historictDto);

		mockMvc.perform(put("/historic/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
}
