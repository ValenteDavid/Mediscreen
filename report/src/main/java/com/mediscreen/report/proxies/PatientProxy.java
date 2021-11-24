package com.mediscreen.report.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.report.controller.dto.PatientDto;

/**
 * Patient proxy
 * 
 * @author David
 * @see com.mediscreen.patient.controller.PatientController
 *
 */
@FeignClient(name = "patient", url = "localhost:8081")
public interface PatientProxy {

	@GetMapping("/patient" + "/{id}")
	PatientDto getPatientById(@PathVariable Integer id);
	
}
