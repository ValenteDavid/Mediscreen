package com.mediscreen.historic.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediscreen.historic.controller.dto.HistoricDto;
import com.mediscreen.historic.dao.HistoricDao;

@RestController
public class HistoricControler {

	private static final Logger log = LoggerFactory.getLogger(HistoricControler.class);

	@Autowired
	private HistoricDao historicDao;

	@GetMapping("/historic/{id}")
	public HistoricDto getHistoricById(@PathVariable Integer id) {
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

}
