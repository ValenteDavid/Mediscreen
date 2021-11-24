package com.mediscreen.report.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mediscreen.report.domain.diabetesLevel;

@Service
public class ReportService {

	private static List<String> triggerTerms = Arrays.asList("Hémoglobine A1C", "Microalbumine", "Taille",
			"Poids", "Fumeur", "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");

	public int occurence(List<String> content) {
		Set<String> ocuurenceSet = new HashSet<>();
		for (String ocuurence : content) {
			String ocuurenceText = ocuurence.toLowerCase();
			for (String triggerTerm : triggerTerms) {
				if (ocuurenceText.indexOf(triggerTerm.toLowerCase()) != -1) {
					ocuurenceSet.add(triggerTerm);
				}
			}
		}
		return ocuurenceSet.size();
	}

	public int age(Date bod) throws IllegalArgumentException {
		int age = Period.between(LocalDate.ofInstant(bod.toInstant(), ZoneId.systemDefault()),
				LocalDate.ofInstant(new Date().toInstant(), ZoneId.systemDefault())).getYears();
		if (age < 0) {
			throw new IllegalArgumentException("Bad date of bith, because before now");
		} else {
			return age;
		}
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	public diabetesLevel diabetesRisk(List<String> content, String sex, int age) {
		int occurenceNumber = occurence(content);

		if (age < 30) {
			if (sex == "M") {
				if (occurenceNumber == 0) {
					return diabetesLevel.None;
				} else if (occurenceNumber < 3) {
					return diabetesLevel.Borderline;
				} else if (occurenceNumber < 5) {
					return diabetesLevel.InDanger;
				} else {
					return diabetesLevel.EarlyOnset;
				}
			} else {
				if (occurenceNumber == 0) {
					return diabetesLevel.None;
				} else if (occurenceNumber < 4) {
					return diabetesLevel.Borderline;
				} else if (occurenceNumber < 7) {
					return diabetesLevel.InDanger;
				} else {
					return diabetesLevel.EarlyOnset;
				}
			}

		} else {
			if (occurenceNumber < 2) {
				return diabetesLevel.None;
			} else if (occurenceNumber < 6) {
				return diabetesLevel.Borderline;
			} else if (occurenceNumber < 8) {
				return diabetesLevel.InDanger;
			} else {
				return diabetesLevel.EarlyOnset;
			}
		}
	}

}
