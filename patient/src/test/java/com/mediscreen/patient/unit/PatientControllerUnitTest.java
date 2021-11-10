package com.mediscreen.patient.unit;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.controller.dto.PatientDto;
import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Sex;

@WebMvcTest
@AutoConfigureMockMvc
public class PatientControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientDao patientDao;

	@Test
	public void getPatientTest_isOk() throws Exception {
		Integer id = 1;
		String firstName = "Test";
		String lastName = "TestNone";

		when(patientDao.findById(id)).thenReturn(Optional.of(
				new Patient(id, firstName, lastName, "2021-01-01", Sex.MALE.getFormat())));

		mockMvc.perform(get("/patient/{0}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName))
				.andExpect(jsonPath("$.dob").value("2021-01-01"))
				.andExpect(jsonPath("$.sex").value(Sex.MALE.getFormat()))
				.andExpect(jsonPath("$.address").doesNotExist())
				.andExpect(jsonPath("$.phone").doesNotExist());
	}

	@Test
	public void getPatientTest_isNotFound() throws Exception {
		Integer id = 1;
		when(patientDao.findById(id)).thenReturn(Optional.empty());

		mockMvc.perform(get("/patient/{0}", id))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getPatientAllTest_isOk() throws Exception {
		List<Patient> patientList = new ArrayList<>();
		patientList.add(new Patient(1, "Test", "TestNone", "2021-01-01", Sex.MALE.getFormat()));
		when(patientDao.findAll()).thenReturn(patientList);

		mockMvc.perform(get("/patient/list"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void updatePatientTest_isOk() throws Exception {
		Integer id = 1;
		
		Patient patientOrigine = new Patient(id, "FirsName", "LastName", "2021-01-01", "M",null,null);
		Patient patient = new Patient(id, "FirsName", "LastName", "2021-01-01", "M","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		when(patientDao.findById(id)).thenReturn(Optional.of(patientOrigine));
		when(patientDao.save(any())).thenReturn(patient);
		
		String body = new ObjectMapper().writeValueAsString(patientDto);
		
		mockMvc.perform(put("/patient/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void updatePatientTest_isBadId() throws Exception {
		Integer id = 1;
		
		Patient patient = new Patient(id, "FirsName", "LastName", "2021-01-01", "M","address","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		when(patientDao.findById(id)).thenReturn(Optional.empty());
		
		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(put("/patient/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updatePatientTest_isBadSex() throws Exception {
		Integer id = 1;
		
		Patient patientOrigine = new Patient(id, "FirsName", "LastName", "2021-01-01", "M",null,null);
		Patient patient = new Patient(id, "FirsName", "LastName", "2021-01-01", "MMM","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		when(patientDao.findById(id)).thenReturn(Optional.of(patientOrigine));
		
		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(put("/patient/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updatePatientTest_isBadDate() throws Exception {
		Integer id = 1;
		
		Patient patientOrigine = new Patient(id, "FirsName", "LastName", "2021-01-01", "M",null,null);
		Patient patient = new Patient(id, "FirsName", "LastName", "2021-31-31", "M","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		when(patientDao.findById(id)).thenReturn(Optional.of(patientOrigine));
		
		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(put("/patient/{0}", id)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addPatientTest_isOk() throws Exception {
		Patient patient = new Patient(null, "FirsName", "LastName", "2021-01-01", "M","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		when(patientDao.save(any())).thenReturn(patient);
		
		String body = new ObjectMapper().writeValueAsString(patientDto);
		
		mockMvc.perform(post("/patient/add")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addPatientTest_isBadSex() throws Exception {
		Patient patient = new Patient(null, "FirsName", "LastName", "2021-01-01", "MMM","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		
		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(post("/patient/add")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void addPatientTest_isBadDate() throws Exception {
		Patient patient = new Patient(null, "FirsName", "LastName", "2021-31-31", "M","","");
		PatientDto patientDto = PatientDto.convertToDto(patient);
		
		String body = new ObjectMapper().writeValueAsString(patientDto);

		mockMvc.perform(post("/patient/add")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
