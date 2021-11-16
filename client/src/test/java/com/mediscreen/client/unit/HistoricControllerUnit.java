package com.mediscreen.client.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mediscreen.client.controller.dto.HistoricDto;
import com.mediscreen.client.controller.dto.PatientDto;
import com.mediscreen.client.proxies.HistoricProxy;
import com.mediscreen.client.proxies.PatientProxy;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoricControllerUnit {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HistoricProxy historicProxy;
	@MockBean
	private PatientProxy patientProxy;

	@Test
	public void showHistoricListTest() throws Exception {
		String id = "61937e698bcc2a1d678ae0b8";
		Integer patientId = 1;
		when(patientProxy.getPatientById(patientId)).thenReturn(
				new PatientDto(1, "", "", "", "M", "", ""));
		HistoricDto historic = new HistoricDto(id, patientId, "note");
		when(historicProxy.getHistoricById(id)).thenReturn(historic);
		
		List<HistoricDto> historicList = new ArrayList<>();
		historicList.add(new HistoricDto(id, 1, "note"));
		when(historicProxy.getHistoricByPatientId(patientId)).thenReturn(historicList);
		
		mockMvc.perform(get("/historic/list/{0}", patientId))
				.andExpect(model().attributeExists("patientDto"))
				.andExpect(model().attributeExists("historics"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void showUpdateHistoricTest() throws Exception {
		String id = "61937e698bcc2a1d678ae0b6";
		Integer patientId = 1;
		when(patientProxy.getPatientById(patientId)).thenReturn(
				new PatientDto(1, "", "", "", "M", "", ""));
		HistoricDto historic = new HistoricDto(id, patientId, "note");
		when(historicProxy.getHistoricById(id)).thenReturn(historic);
		
		mockMvc.perform(get("/historic/update/{0}/{1}",patientId,id))
		.andExpect(model().attribute("title","Edit historic"))
		.andExpect(model().attribute("titleButton","Edit"))
		.andExpect(model().attributeExists("patientDto"))
		.andExpect(model().attributeExists("historicDto"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showAddHistoricTest() throws Exception {
		Integer patientId = 1;
		when(patientProxy.getPatientById(patientId)).thenReturn(new PatientDto());
		mockMvc.perform(get("/historic/add/{0}",patientId))
		.andExpect(status().isOk());
	}
}
