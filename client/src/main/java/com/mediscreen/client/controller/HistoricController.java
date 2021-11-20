package com.mediscreen.client.controller;

import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mediscreen.client.controller.dto.HistoricDto;
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
	 * 
	 * @param model
	 * 
	 * @param httpServletRequest
	 * 
	 * @return
	 */
	@GetMapping("/historic/list/{patientId}")
	public String showHistoricList(@PathVariable Integer patientId, Model model,
			HttpServletRequest httpServletRequest) {
		log.info("Call /historic/list/{}", patientId);
		listAttribute(model, patientId);
		log.debug("Attribute { historics : " + model.getAttribute("historics"));
		log.info("Return /historic/list/{} : {}", patientId, "historic/list");
		return "historic/list";
	}

	/**
	 * Show UI edit historic
	 * 
	 * @param id : historic id
	 * @return UI and attribute
	 */
	@GetMapping("/historic/update/{patientId}/{id}")
	public String showUpdatePatient(@PathVariable String id, @PathVariable Integer patientId, Model model,
			HttpServletRequest httpServletRequest) {
		log.info("Call /historic/update/" + id);
		updateAttribute(model, patientId, id);
		model.addAttribute("historicDto", historicProxy.getHistoricById(id));
		log.debug("Attribute { historicDto : " + model.getAttribute("historicDto") + " }");
		log.info("Return /historic/update/" + id + " : historic/add_update ");
		return "historic/add_update";
	}
	
	/**
	 * Show UI add historic
	 * 
	 * @param id : historic id
	 * @return UI and attribute
	 */
	@GetMapping("/historic/add/{patientId}")
	public String showAddHistoric(@PathVariable Integer patientId,Model model, HttpServletRequest httpServletRequest) {
		log.info("Call /historic/add");
		addAttribute(model,patientId);
		model.addAttribute("historicDto", new HistoricDto(null,patientId,null));
		log.debug("Attribute { historicDto : " + model.getAttribute("historicDto") + " }");
		log.info("Return /historic/add" + " : patient/add_update ");
		return "historic/add_update";
	}

	/**
	 * Validate data after send edit or save historic
	 * 
	 * @param historicDto : historicDto
	 * @return
	 */
	@PostMapping("/historic/validate")
	public String validateHistoric(@ModelAttribute(name = "patientId") Integer patientId,
			@Valid HistoricDto historicDto, BindingResult result, Model model,
			HttpServletRequest httpServletRequest) {
		log.info("Call /historic/validate, param { historicDto : " + historicDto + ", patientId : " + patientId + "}");
		log.debug("Custom Control");
		String format = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);

		if (!result.hasErrors()) {
			try {
				if (historicDto.getId() != null) {
					log.debug("Send update");
					historicProxy.updateHsitoric(historicDto.getId(), historicDto);
				} else {
					log.debug("Send save");
					historicProxy.addHistoric(historicDto);
				}
				log.info("Return /historic/validate : " + "redirect:/historic/list/{}",patientId);
				return "redirect:/historic/list/" + patientId;
			} catch (Exception e) {
				log.warn("Control Historic ERROR");
				return redirectSaveUpdate(historicDto, model, patientId, historicDto.getId());
			}
		} else {
			log.debug("Error : {}",result.getAllErrors().stream().map(Object::toString).collect(Collectors.joining(",")));
			return redirectSaveUpdate(historicDto, model, patientId, historicDto.getId());
		}
	}

	private Model listAttribute(Model model, Integer patientId) {
		model.addAttribute("patientDto", patientProxy.getPatientById(patientId));
		model.addAttribute("historics", historicProxy.getHistoricByPatientId(patientId));
		return model;
	}

	private Model addAttribute(Model model,Integer patientId) {
		model.addAttribute("title", "New historic");
		model.addAttribute("titleButton", "Add");
		model.addAttribute("patientDto", patientProxy.getPatientById(patientId));
		
		return model;
	}

	private Model updateAttribute(Model model, Integer patientId, String id) {
		model.addAttribute("title", "Edit historic");
		model.addAttribute("titleButton", "Edit");
		model.addAttribute("patientDto", patientProxy.getPatientById(patientId));
		return model;
	}

	private String redirectSaveUpdate(HistoricDto historicDto, Model model, Integer patientId, String id) {
		if (historicDto.getId() != null) {
			updateAttribute(model, patientId, id);
			log.info("Return /historic/validate : " + "historic/add_update");
			return "historic/add_update";
		} else {
			addAttribute(model,patientId);
			log.info("Return /historic/validate : " + "historic/add_update");
			return "historic/add_update";
		}
	}

}
