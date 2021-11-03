package com.mediscreen.patient.domain;

/**
 * Enum Sex M or F
 * @author David
 *
 */
public enum Sex {

	MALE("M"),
	FEMALE("F");

	private String format;

	Sex(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
	
	public static boolean findExist(String formatExist) {
		for (Sex sex : Sex.values()) {
			if (sex.format.equals(formatExist)) {
				return true;
			}
		}
		return false;
	}

}
