package com.mediscreen.historic.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mediscreen.historic.domain.Historic;

public interface HistoricDao extends MongoRepository<Historic, Integer> {
	
	List<Historic> findByPatientId(Integer patientId);

}
