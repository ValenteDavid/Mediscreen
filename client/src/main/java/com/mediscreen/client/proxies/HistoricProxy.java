package com.mediscreen.client.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	HistoricDto getHistoricById(@PathVariable Integer id);

	@GetMapping("/historic/list/{patientId}")
	List<HistoricDto> getHistoricByPatientId(@PathVariable Integer patientId);

}
