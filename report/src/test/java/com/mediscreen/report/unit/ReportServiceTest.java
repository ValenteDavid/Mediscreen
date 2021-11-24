package com.mediscreen.report.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.report.service.ReportService;

@SpringBootTest
public class ReportServiceTest {

	@Autowired
	private ReportService reportService;
	
	@Test
	public void occurenceTest_DistinctMode() {
		List<String> stringList = new ArrayList<>();
		stringList.add("Hémoglobine A1C Microalbumine Taille Poids Fumeur Anormal Cholestérol Vertige Rechute Réaction Anticorps");
		stringList.add("Hémoglobine A1C Microalbumine Taille Poids Fumeur Anormal Cholestérol Vertige Rechute Réaction Anticorps");
		int result = reportService.occurence(stringList);
		assertEquals(11, result);
	}
	
	@Test
	public void occurenceTest_CountMode() {
		List<String> stringList = new ArrayList<>();
		stringList.add("Le patient déclare qu'il « se sent très bien »\r\n"
				+ "Poids égal ou inférieur au poids recommandé");
		stringList.add("Le patient déclare qu'il se sent fatigué pendant la journée\r\n"
				+ "Il se plaint également de douleurs musculaires\r\n"
				+ "Tests de laboratoire indiquant une microalbumine élevée");
		stringList.add("Le patient déclare qu'il ne se sent pas si fatigué que ça\r\n"
				+ "Fumeur, il a arrêté dans les 12 mois précédents\r\n"
				+ "Tests de laboratoire indiquant que les anticorps sont élevés");
		int result = reportService.occurence(stringList);
		assertEquals(4, result);
	}
	
	
	
	
	
	
}
