package com.mediscreen.historic.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.historic.dao.HistoricDao;
import com.mediscreen.historic.domain.Historic;

@SpringBootTest
public class HistoricDaoIntegTest {
	
	@Autowired
	private HistoricDao historicDao;
	
	@Test
	public void findByPatientIdTest() {
		Integer patientId = 2;
		List<Historic> historic = historicDao.findByPatientId(patientId);
		assertTrue(historic.stream().anyMatch(historicItem-> patientId.equals(historicItem.getPatientId()))); 

	}
	
	@Test 
	public void cruTest() {
		Historic historic = new Historic(null, 1, "Note");
		Historic historicSave =  historicDao.save(historic);
		Historic historicGet = historicDao.findById(historicSave.getId()).get();
		assertThat(historicSave).usingRecursiveComparison().isEqualTo(historicGet);
		
		historic =  new Historic(historicSave.getId(), 1, "NoteNote");
		Historic historicUpdate =  historicDao.save(historic);
		historicGet = historicDao.findById(historic.getId()).get();
		assertThat(historicUpdate).usingRecursiveComparison().isEqualTo(historicGet);
		
	}

}
