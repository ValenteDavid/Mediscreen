package com.mediscreen.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.client.proxies.ReportProxy;

@Controller
public class ReportController {
	
	@Autowired
	private ReportProxy reportProxy;
	
	@GetMapping("/generateReport/{patientId}")
	public String generateReport(@PathVariable int patientId,Model model) {
		
		String value = reportProxy.generateRepport(patientId);
		model.addAttribute("reportResult",value);
		
		return "report/report";
	}

}
