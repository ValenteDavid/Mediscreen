package com.tourguide.patient.domain;

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

}
