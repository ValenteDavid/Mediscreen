package com.tourguide.patient.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tourguide.patient.domain.Patient;

/**
 * Patient Dao
 * @author David
 *
 */
@Repository
public interface PatientDao extends CrudRepository<Patient, Integer>{

}
