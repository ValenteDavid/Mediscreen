package com.mediscreen.report.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.report.domain.diabetesLevel;
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
	
	@Test
	public void ageTest() {
		int ageExpect = 55;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-ageExpect);
		Date birthDate = calendar.getTime();
		
        int actual = reportService.age(birthDate);
        assertEquals(ageExpect, actual);
	}
	
	@Test
	public void ageTest_NegativeAge() {
        assertThrows(IllegalArgumentException.class,() ->{
        	
    		Calendar calendar = Calendar.getInstance();
    		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+10);
    		Date birthDate = calendar.getTime();
    		
            reportService.age(birthDate);
        	
        });
	}
	
	private static Stream<Arguments> provideDiabtesRisk(){
		return Stream.of(
				Arguments.of(0,"M",25,diabetesLevel.None),
				Arguments.of(0,"F",25,diabetesLevel.None),
				Arguments.of(0,"M",30,diabetesLevel.None),
				Arguments.of(0,"F",30,diabetesLevel.None),
				//1
				Arguments.of(1,"M",25,diabetesLevel.None),
				Arguments.of(1,"F",25,diabetesLevel.None),
				Arguments.of(1,"M",30,diabetesLevel.None),
				Arguments.of(1,"F",30,diabetesLevel.None),
				//2
				Arguments.of(2,"M",25,diabetesLevel.None),
				Arguments.of(2,"M",30,diabetesLevel.Borderline),
				Arguments.of(2,"F",25,diabetesLevel.None),
				Arguments.of(2,"F",30,diabetesLevel.Borderline),
				//3
				Arguments.of(3,"M",25,diabetesLevel.InDanger),
				Arguments.of(3,"M",30,diabetesLevel.Borderline),
				Arguments.of(3,"F",25,diabetesLevel.None),
				Arguments.of(3,"F",30,diabetesLevel.Borderline),
				//4
				Arguments.of(4,"M",25,diabetesLevel.InDanger),
				Arguments.of(4,"M",30,diabetesLevel.Borderline),
				Arguments.of(4,"F",25,diabetesLevel.InDanger),
				Arguments.of(4,"F",30,diabetesLevel.Borderline),
				//5
				Arguments.of(5,"M",25,diabetesLevel.EarlyOnset),
				Arguments.of(5,"M",30,diabetesLevel.Borderline),
				Arguments.of(5,"F",25,diabetesLevel.InDanger),
				Arguments.of(5,"F",30,diabetesLevel.Borderline),
				//6
				Arguments.of(6,"M",25,diabetesLevel.EarlyOnset),
				Arguments.of(6,"M",30,diabetesLevel.InDanger),
				Arguments.of(6,"F",25,diabetesLevel.InDanger),
				Arguments.of(6,"F",30,diabetesLevel.InDanger),
				//7
				Arguments.of(7,"M",25,diabetesLevel.EarlyOnset),
				Arguments.of(7,"M",30,diabetesLevel.InDanger),
				Arguments.of(7,"F",25,diabetesLevel.EarlyOnset),
				Arguments.of(7,"F",30,diabetesLevel.InDanger),
				//8
				Arguments.of(8,"M",25,diabetesLevel.EarlyOnset),
				Arguments.of(8,"M",30,diabetesLevel.EarlyOnset),
				Arguments.of(8,"F",25,diabetesLevel.EarlyOnset),
				Arguments.of(8,"F",30,diabetesLevel.EarlyOnset),
				//9
				Arguments.of(9,"M",25,diabetesLevel.EarlyOnset),
				Arguments.of(9,"M",30,diabetesLevel.EarlyOnset),
				Arguments.of(9,"F",25,diabetesLevel.EarlyOnset),
				Arguments.of(9,"F",30,diabetesLevel.EarlyOnset)
				);
	}
	
	@ParameterizedTest
	@MethodSource("provideDiabtesRisk")
	public void diabetesRisk(int occurenceNumber,String sex,int age,diabetesLevel resultExpect) {
		diabetesLevel result = reportService.diabetesRisk(occurenceNumber, sex, age);
		assertEquals(result, result);
	}
}
