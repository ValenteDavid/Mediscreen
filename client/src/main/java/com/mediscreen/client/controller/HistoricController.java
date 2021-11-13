package com.mediscreen.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.client.proxies.HistoricProxy;
import com.mediscreen.client.proxies.PatientProxy;

@Controller
public class HistoricController {

	private static final Logger log = LoggerFactory.getLogger(HistoricController.class);

	@Autowired
	private HistoricProxy historicProxy;
	
	@Autowired
	private PatientProxy patientProxy;

	/*
	 * Show UI all historic
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping("/historic/list/{patientId}")
	public String showHistoricList(@PathVariable Integer patientId, Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /historic/list/{}",patientId);
		model.addAttribute("patientDto", patientProxy.getPatientById(patientId));
		model.addAttribute("historics", historicProxy.getHistoricByPatientId(patientId));
		log.debug("Attribute { historics : " + model.getAttribute("historics"));
		log.info("Return /historic/list/{} : {}",patientId,"historic/list");
		return "historic/list";
	}

}
