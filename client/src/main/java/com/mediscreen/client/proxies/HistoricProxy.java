package com.mediscreen.client.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mediscreen.client.controller.dto.HistoricDto;

/**
 * Patient proxy
 * 
 * @author David
 * @see com.mediscreen.patient.controller.PatientController
 *
 */
@FeignClient(name = "historic", url = "localhost:8082")
public interface HistoricProxy {

	@GetMapping("/historic/{id}")
	HistoricDto getHistoricById(@PathVariable String id);

	@GetMapping("/historic/list/{patientId}")
	List<HistoricDto> getHistoricByPatientId(@PathVariable Integer patientId);
	
	@PutMapping("/historic" + "/{id}")
	HistoricDto updateHsitoric(@PathVariable String id,@Valid @RequestBody HistoricDto historicDto);
	
}
