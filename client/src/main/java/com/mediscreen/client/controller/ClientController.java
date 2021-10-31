package com.mediscreen.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mediscreen.client.proxies.PatientProxy;

/**
 * Client controller
 * @author David
 *
 */
@Controller
public class ClientController {
	
	/**
	 * @see PatientProxy
	 */
	@Autowired
	private PatientProxy patientProxy;

	@GetMapping("/patients")
	public String showPatientList(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute("patients",patientProxy.getAllPatient());
		return"patient/list";
	}
}
