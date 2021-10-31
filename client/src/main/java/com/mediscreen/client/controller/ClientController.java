package com.mediscreen.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);

	/**
	 * @see PatientProxy
	 */
	@Autowired
	private PatientProxy patientProxy;

	@GetMapping("/patients")
	public String showPatientList(Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /patients");
		model.addAttribute("patients",patientProxy.getAllPatient());
		log.debug("Attribute { patients : " + model.getAttribute("patients"));
		log.info("Return /patients : patient/list ");
		return"patient/list";
	}
}
