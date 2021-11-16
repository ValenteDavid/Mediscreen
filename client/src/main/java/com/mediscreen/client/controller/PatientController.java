package com.mediscreen.client.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mediscreen.client.controller.dto.PatientDto;
import com.mediscreen.client.proxies.PatientProxy;

/**
 * Client controller
 * 
 * @author David
 *
 */
@Controller
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);

	/**
	 * @see PatientProxy
	 */
	@Autowired
	private PatientProxy patientProxy;

	/**
	 * Show UI all patient
	 * 
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping("/patient/list")
	public String showPatientList(Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /patients");
		model.addAttribute("patients", patientProxy.getAllPatient());
		log.debug("Attribute { patients : " + model.getAttribute("patients"));
		log.info("Return /patients : patient/list ");
		return "patient/list";
	}

	/**
	 * Show UI edit patient
	 * 
	 * @param id : patient id
	 * @return UI and attribute
	 */
	@GetMapping("/patient/update/{id}")
	public String showUpdatePatient(@PathVariable Integer id, Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /patient/update/" + id);
		updateAttribute(model);
		model.addAttribute("patientDto", patientProxy.getPatientById(id));
		log.debug("Attribute { patientDto : " + model.getAttribute("patientDto") + " }");
		log.info("Return /patient/update/" + id + " : patient/add_update ");
		return "patient/add_update";
	}

	/*
	 * Show UI add patient
	 * 
	 * @param id : patient id
	 * 
	 * @return UI and attribute
	 */
	@GetMapping("/patient/add")
	public String showAddPatient(Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /patient/add");
		addAttribute(model);
		model.addAttribute("patientDto", new PatientDto());
		log.debug("Attribute { patientDto : " + model.getAttribute("patientDto") + " }");
		log.info("Return /patient/add" + " : patient/add_update ");
		return "patient/add_update";
	}

	/**
	 * Validate data after send edit or save patient
	 * 
	 * @param patientDto : patientDto
	 * @return
	 */
	@PostMapping("/patient/validate")
	public String validatePatient(@Valid PatientDto patientDto, BindingResult result, Model model,
			HttpServletRequest httpServletRequest) {
		log.info("Call /patient/validate, param { patientDto : " + patientDto + "}");
		log.debug("Custom Control");
		String format = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			log.debug("Control : dob");
			df.parse(patientDto.getDob());
			log.debug("Control OK : dob");
		} catch (ParseException e) {
			log.warn("Control ERROR : dob format");
			result.addError(new FieldError("patientDto", "dob", "Date of birth format is " + format));
			return redirectSaveUpdate(patientDto, model);
		}

		if (!result.hasErrors()) {
			try {
				if (patientDto.getId() != null) {
					log.debug("Send update");
					patientProxy.updatePatient(patientDto.getId(),patientDto);
				} else {
					log.debug("Send add");
					patientProxy.addPatient(patientDto);
				}
				log.info("Return /patient/validate : " + "redirect:/patient/list");
				return "redirect:/patient/list";
			} catch (Exception e) {
				log.warn("Control Patient ERROR");
				return redirectSaveUpdate(patientDto, model);
			}
		} else {
			log.debug("Error : {}",result.getAllErrors().stream().map(Object::toString).collect(Collectors.joining(",")));
			return redirectSaveUpdate(patientDto, model);
		}
	}

	private Model addAttribute(Model model) {
		model.addAttribute("title", "New patient");
		model.addAttribute("titleForm", "New patient");
		model.addAttribute("titleButton", "Add");
		return model;
	}

	private Model updateAttribute(Model model) {
		model.addAttribute("title", "Edit patient");
		model.addAttribute("titleForm", "Edit patient");
		model.addAttribute("titleButton", "Edit");
		return model;
	}

	private String redirectSaveUpdate(PatientDto patientDto, Model model) {
		if (patientDto.getId() != null) {
			updateAttribute(model);
			log.info("Return /patient/validate : " + "patient/add_update");
			return "patient/add_update";
		} else {
			addAttribute(model);
			log.info("Return /patient/validate : " + "patient/add_update");
			return "patient/add_update";
		}
	}

}
