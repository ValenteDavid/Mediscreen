package com.mediscreen.historic.unit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.historic.controller.dto.HistoricDto;
import com.mediscreen.historic.dao.HistoricDao;
import com.mediscreen.historic.domain.Historic;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoricControlerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HistoricDao historicDao;
	
	@Test
	public void getHistoricByIdTest_isOk() throws Exception {
		String id = "61937e698bcc2a1d678ae0b8";
		
		when(historicDao.findById(id)).thenReturn(Optional.of(
				new Historic(id, 2, "note")));


		mockMvc.perform(get("/historic/{0}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.patientId").value(2))
				.andExpect(jsonPath("$.note").value("note"));
	}
	
	@Test
	public void getHistoricByIdTest_isNotFound() throws Exception {
		String id = "61937e698bcc2a1d678ae0b8";
		when(historicDao.findById(id)).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/historic/{0}", id))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getHistoricByPatientIdTest_isOk() throws Exception {
		String id = "61937e698bcc2a1d678ae0b8";
		Integer patientId = 1;
		List<Historic> historicList = new ArrayList<>();
		historicList.add(new Historic(id, 1, "note"));
		when(historicDao.findByPatientId(patientId)).thenReturn(historicList);
		
		mockMvc.perform(get("/historic/list/{0}",patientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void updateHistoricTest_isOk() throws Exception {
		String id = "61937e698bcc2a1d678ae0b9";
		
		Historic historicOrigine =new Historic(id, 2, "Note");
		Historic historic = new Historic(id, 2, "NOTENOTENOTE");
		HistoricDto historicDto = HistoricDto.convertToDto(historic);
		when(historicDao.findById(id)).thenReturn(Optional.of(historicOrigine));
		when(historicDao.save(any())).thenReturn(historic);

		String body = new ObjectMapper().writeValueAsString(historicDto);

		mockMvc.perform(put("/historic/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateHistoricTest_isEmptyNote() throws Exception {
		String id = "61937e698bcc2a1d678ae0b9";
		
		Historic historicOrigine =new Historic(id, 2, "Note");
		Historic historic = new Historic(id, 2, "");
		HistoricDto historicDto = HistoricDto.convertToDto(historic);
		when(historicDao.findById(id)).thenReturn(Optional.of(historicOrigine));
		when(historicDao.save(any())).thenReturn(historic);

		String body = new ObjectMapper().writeValueAsString(historicDto);

		mockMvc.perform(put("/historic/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addHistoricTest_isOk() throws Exception {
		Historic historic = new Historic(null, 2, "NOTENOTENOTE");
		HistoricDto historicDto = HistoricDto.convertToDto(historic);
		when(historicDao.save(any())).thenReturn(historic);

		String body = new ObjectMapper().writeValueAsString(historicDto);

		mockMvc.perform(post("/historic/add")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void addHistoricTest_isEmptyNote() throws Exception {
		Historic historic = new Historic(null, 2, "");
		HistoricDto historicDto = HistoricDto.convertToDto(historic);
		when(historicDao.save(any())).thenReturn(historic);

		String body = new ObjectMapper().writeValueAsString(historicDto);

		mockMvc.perform(post("/historic/add")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
