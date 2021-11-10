package com.mediscreen.client.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mediscreen.client.controller.dto.PatientDto;

/**
 * Patient proxy
 * 
 * @author David
 * @see com.mediscreen.patient.controller.PatientController
 *
 */
@FeignClient(name = "patient", url = "localhost:8081")
public interface PatientProxy {

	@GetMapping("/patient/list")
	List<PatientDto> getAllPatient();
	
	@GetMapping("/patient" + "/{id}")
	PatientDto getPatientById(@PathVariable Integer id);
	
	@PostMapping("/patient/add")
	PatientDto addPatient(@Valid @RequestBody PatientDto patientDto);
		
	@PutMapping("/patient" + "/{id}")
	PatientDto updatePatient(@PathVariable Integer id,@Valid @RequestBody PatientDto patientdto);

}
