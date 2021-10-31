package com.mediscreen.patient.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.patient.controller.dto.PatientDto;
import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.domain.Patient;

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
	 * @see PatientDto
	 * @see Patient
	 */
	@GetMapping("/patient" + "/{id}")
	public PatientDto getPatientById(@PathVariable Integer id) {
		log.info("Call /patient, param : { id : " + id + "}");
		PatientDto patientDto = PatientDto.convertToDto(patientDao.findById(id).get());
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

}
