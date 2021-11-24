package com.mediscreen.report.domain;

public enum diabetesLevel {
	None("none"), Borderline("borderline"), InDanger("in danger"), EarlyOnset("early onset");

	private String description;

	diabetesLevel(String description) {
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
