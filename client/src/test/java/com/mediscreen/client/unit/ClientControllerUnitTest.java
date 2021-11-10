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

import com.mediscreen.client.controller.dto.PatientDto;
import com.mediscreen.client.proxies.PatientProxy;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientProxy patientProxy;
	
	@Test
	public void showPatientListTest() throws Exception {
		List<PatientDto> patientList = new ArrayList<>();
		patientList.add(new PatientDto(1,"firstName", "lastName", "2021-01-01","F","address","000-111-222"));
		when(patientProxy.getAllPatient()).thenReturn(patientList);
		
		mockMvc.perform(get("/patient/list"))
		.andExpect(model().attributeExists("patients"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showUpdatePatientTest() throws Exception {
		Integer id = 1;
		when(patientProxy.getPatientById(id)).thenReturn(new PatientDto(1,"firstName", "lastName", "2021-01-01","F","address","000-111-222"));
		mockMvc.perform(get("/patient/update/{0}",id))
		.andExpect(status().isOk());
	}
	
	@Test
	public void showAddPatientTest() throws Exception {
		mockMvc.perform(get("/patient/add"))
		.andExpect(status().isOk());
	}
}

