package com.mediscreen.report.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.report.controller.dto.HistoricDto;
import com.mediscreen.report.controller.dto.PatientDto;
import com.mediscreen.report.proxies.HistoricProxy;
import com.mediscreen.report.proxies.PatientProxy;
import com.mediscreen.report.service.ReportService;

/**
 * Report Controller
 * @author David
 *
 */
@RestController
public class ReportController {

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService repportService;

	@Autowired
	private HistoricProxy historicProxy;
	@Autowired
	private PatientProxy patientProxy;

	@GetMapping("/assessments/{patientId}")
	public String generateRepport(@PathVariable int patientId, HttpServletResponse response) {
		log.info("Call /assessments/" + patientId);
		PatientDto patientDto = patientProxy.getPatientById(patientId);
		log.info("Control : sex");
		if (!(patientDto.getSex().equals("M") || patientDto.getSex().equals("F"))) {
			log.warn("Control ERROR : sex format");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		log.info("Control OK : sex");
		log.info("Control date format");
		int age = 0;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			log.debug("Control : dob");
			age = repportService.age(sdf.parse(patientDto.getDob()));
			log.debug("Control OK : dob");
		} catch (ParseException e) {
			log.warn("Control ERROR : dob format");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch (IllegalArgumentException e) {
			log.warn("Control ERROR : {}",e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		List<HistoricDto> historicList = historicProxy.getHistoricByPatientId(patientId);
		log.debug("historicList = " +historicList);
		List<String> historicListContent = historicList.stream().map(historic -> historic.getNote())
				.collect(Collectors.toList());
		log.debug("historicListContent = " +historicListContent);

		int occurenceNumber = repportService.occurence(historicListContent);
		
		String diabeteRisk = repportService.diabetesRisk(occurenceNumber, patientDto.getSex(), age)
				.getDescription();
		String responseContent = String.format("Patient: %s %s (age %d) diabetes assessment is: %s",
				patientDto.getFirstName(),
				patientDto.getLastName(), age, diabeteRisk);
		return responseContent;
	}

}
