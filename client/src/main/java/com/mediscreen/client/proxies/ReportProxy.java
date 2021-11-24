package com.mediscreen.client.proxies;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Patient proxy
 * 
 * @author David
 * @see com.mediscreen.patient.controller.PatientController
 *
 */
@FeignClient(name = "report", url = "localhost:8083")
public interface ReportProxy {

	@GetMapping("/assessments/{patientId}")
	String generateRepport(@PathVariable int patientId);
}
