package com.mediscreen.patient.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediscreen.patient.controller.dto.PatientDto;
import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Sex;

import javassist.NotFoundException;

/**
 * Patient Controller
 * 
 * @author David
 *
 */
@RestController
public class PatientController {
	private static final Logger log = LoggerFactory.getLogger(PatientController.class);

	/**
	 * @see PatientDao
	 */
	@Autowired
	private PatientDao patientDao;

	/**
	 * Endpoint getPatient
	 * 
	 * @param id patient id
	 * @return the patient at this id
	 * @throws NotFoundException
	 * @see PatientDto
	 * @see Patient
	 */
	@GetMapping("/patient" + "/{id}")
	public PatientDto getPatientById(@PathVariable Integer id) {
		log.info("Call /patient/{}",id);
		PatientDto patientDto = PatientDto.convertToDto(patientDao.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find patient")));
		log.info("Response /patient/{}  : {}",id,patientDto);
		return patientDto;
	}

	/**
	 * Endpoint getAllPatient
	 * 
	 * @return All Patient
	 * @see PatientDto
	 * @see Patient
	 */
	@GetMapping("/patient/list")
	public List<PatientDto> getAllPatient() {
		log.info("Call /patients");
		List<PatientDto> patientDtoList = StreamSupport.stream(patientDao.findAll().spliterator(), false)
				.map(patient -> PatientDto.convertToDto(patient))
				.collect(Collectors.toList());
		log.info("Response /patients  : {}", patientDtoList);
		return patientDtoList;
	}

	@PutMapping("/patient" + "/{id}")
	public PatientDto updatePatient(@PathVariable Integer id,@Valid @RequestBody PatientDto patientDto,HttpServletResponse response) {
		log.info("Call /patient/{}, body : patientdto = {}",id,patientDto);
		log.debug("Control : id");
		PatientDto patientOrigine = PatientDto.convertToDto(patientDao.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find patient")));
		log.debug("Control OK : id");
		
		if(!validatePatient(patientDto, response)) {
			log.info("Response /patient  : {}",patientOrigine);
			return patientOrigine;
		};
		
		Patient patient = PatientDto.convertToDomain(patientDto);
		log.debug(patient.toString());
		patientDto = PatientDto.convertToDto(patientDao.save(patient));
		log.info("Response /patient  : {}", patientDto);
		return patientDto;
	}
	
	@PostMapping("/patient/add")
	public PatientDto addPatient(@Valid @RequestBody PatientDto patientDto,HttpServletResponse response,Model model,BindingResult result) {
		log.info("Call /patient/add");
		
		if(!validatePatient(patientDto, response)) {
			return null;
		};
		
		Patient patient = PatientDto.convertToDomain(patientDto);
		log.debug(patient.toString());
		patientDto = PatientDto.convertToDto(patientDao.save(patient));
		log.info("Response /patient/add  : {}",patientDto);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return patientDto;
	}
	
	/**
	 * Data validation of Patient
	 * @param patientDto : patientDto
	 * @param response
	 * @return True if all data is valid else false
	 * @see PatientDto
	 */
	private boolean validatePatient(@Valid PatientDto patientDto,HttpServletResponse response) {
		log.debug("Control : sex");
		if (!Sex.findExist(patientDto.getSex())){
			log.debug("Control ERROR: sex");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}
		log.debug("Control OK: sex");
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			log.debug("Control : dob");
			sdf.parse(patientDto.getDob());
		}catch (ParseException e) {
			log.debug("Control ERROR : dob");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}
		log.debug("Control OK : dob");
		
		return true;
	}

}
