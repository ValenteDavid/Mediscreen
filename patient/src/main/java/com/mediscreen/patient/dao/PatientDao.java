package com.mediscreen.patient.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.patient.domain.Patient;

/**
 * Patient Dao
 * @author David
 *
 */
@Repository
public interface PatientDao extends CrudRepository<Patient, Integer>{

}
