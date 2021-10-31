package com.mediscreen.client.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

	@GetMapping("/patients")
	List<PatientDto> getAllPatient();

}
