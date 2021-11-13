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
	public void showPatientListTest() throws Exception {
		Integer patientId = 1;
		
		PatientDto patientDto = new PatientDto(1,"firstName", "lastName", "2021-01-01","F","address","000-111-222");
		when(patientProxy.getPatientById(patientId)).thenReturn(patientDto);
		List<HistoricDto> historicList = new ArrayList<>();
		historicList.add(new HistoricDto("1",patientId,"Lorem note"));
		when(historicProxy.getHistoricByPatientId(patientId)).thenReturn(historicList);
		
		
		mockMvc.perform(get("/historic/list/{0}", patientId))
				.andExpect(model().attributeExists("patientDto"))
				.andExpect(model().attributeExists("historics"))
				.andExpect(status().isOk());
	}
}
