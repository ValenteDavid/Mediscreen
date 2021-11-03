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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		log.info("Call /patient, param : { id : " + id + "}");
		PatientDto patientDto = PatientDto.convertToDto(patientDao.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find patient")));
		log.info("Response /patient  : " + patientDto);
		return patientDto;
	}

	/**
	 * Endpoint getAllPatient
	 * 
	 * @return All Patient
	 * @see PatientDto
	 * @see Patient
	 */
	@GetMapping("/patients")
	public List<PatientDto> getAllPatient() {
		log.info("Call /patients");
		List<PatientDto> patientDtoList = StreamSupport.stream(patientDao.findAll().spliterator(), false)
				.map(patient -> PatientDto.convertToDto(patient))
				.collect(Collectors.toList());
		log.info("Response /patients  : " + patientDtoList);
		return patientDtoList;
	}

	@PutMapping("/patient" + "/{id}")
	public PatientDto updatePatient(@PathVariable Integer id,@Valid @RequestBody PatientDto patientdto,HttpServletResponse response,Model model,BindingResult result) {
		log.info("Call /patient, param : { id : " + id + ", patientdto : " + patientdto + "}");
		log.debug("Control : id");
		PatientDto patientOrigine = PatientDto.convertToDto(patientDao.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find patient")));
		log.debug("Control OK : id");
		
		log.debug("Control : sex");
		if (!Sex.findExist(patientdto.getSex())){
			log.debug("Control ERROR: sex");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return patientOrigine;
		}
		log.debug("Control OK: sex");
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			log.debug("Control : dob");
			sdf.parse(patientdto.getDob());
		}catch (ParseException e) {
			log.debug("Control ERROR : dob");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.addError(new ObjectError("patientdto", "Bad date of birth format, correct format example 2021-01-01"));
			return patientOrigine;
		}
		log.debug("Control OK : dob");
		
		Patient patient = PatientDto.convertToDomain(patientdto);
		log.debug(patient.toString());
		patientdto = PatientDto.convertToDto(patientDao.save(patient));
		log.info("Response /patient  : " + patientdto);
		return patientdto;
	}

}
