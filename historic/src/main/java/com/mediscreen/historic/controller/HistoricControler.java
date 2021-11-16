package com.mediscreen.historic.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediscreen.historic.controller.dto.HistoricDto;
import com.mediscreen.historic.dao.HistoricDao;
import com.mediscreen.historic.domain.Historic;

@RestController
public class HistoricControler {

	private static final Logger log = LoggerFactory.getLogger(HistoricControler.class);

	@Autowired
	private HistoricDao historicDao;

	@GetMapping("/historic/{id}")
	public HistoricDto getHistoricById(@PathVariable String id) {
		log.info("Call /historic/, param : { id : " + id + "}");
		HistoricDto historicDto = HistoricDto.convertToDto(historicDao.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find historic")));
		log.info("Response /historic/{}  : {}",id,historicDto);
		return historicDto;
	}

	@GetMapping("/historic/list/{patientId}")
	public List<HistoricDto> getHistoricByPatientId(@PathVariable Integer patientId) {
		log.info("Call /historic/list/{}",patientId);
		List<HistoricDto> historicDtoList = StreamSupport.stream(historicDao.findByPatientId(patientId).spliterator(), false)
				.map(patient -> HistoricDto.convertToDto(patient))
				.collect(Collectors.toList());
		log.info("Response /historic/list/{} : {}   ",patientId, historicDtoList);
		return historicDtoList;
	}
	
	@PutMapping("/historic/update/{id}")
	public HistoricDto updateHsitoric(@PathVariable String id,@Valid @RequestBody HistoricDto historicDto,HttpServletResponse response) {
		log.info("Call /historic/{}, body : historicDto = {}",id,historicDto);
		log.debug("Control : id");
		HistoricDto historicOrigine = HistoricDto.convertToDto(historicDao.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find historic")));
		log.debug("Control OK : id");
		
		Historic historic = HistoricDto.convertToDomain(historicDto);
		log.debug(historic.toString());
		historicDto = HistoricDto.convertToDto(historicDao.save(historic));
		log.info("Response /historic  : {}", historicDto);
		return historicDto;
	}
	
	
	/**
	 * Add historic
	 * @param historicDto
	 * @param response
	 * @param model
	 * @param result
	 * @return historic save
	 */
	@PostMapping("/historic/add")
	public HistoricDto addHistoric(@Valid @RequestBody HistoricDto historicDto,HttpServletResponse response,Model model,BindingResult result) {
		log.info("Call /historic/add");
		
		Historic historic = HistoricDto.convertToDomain(historicDto);
		log.debug(historic.toString());
		historicDto = HistoricDto.convertToDto(historicDao.save(historic));
		log.info("Response /patient/add  : {}",historicDto);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return historicDto;
	}
	
}
