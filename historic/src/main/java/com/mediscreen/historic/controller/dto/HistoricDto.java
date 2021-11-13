package com.mediscreen.historic.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mediscreen.historic.domain.Historic;

public class HistoricDto {

	private String id;
	@NotNull
	private Integer patientId;
	@NotEmpty
	private String note;
	
	public HistoricDto() {
	}
	
	public HistoricDto(String id, @NotNull Integer patientId, @NotEmpty String note) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "HistoricDto [id=" + id + ", patientId=" + patientId + ", note=" + note + "]";
	}

	public static HistoricDto convertToDto(Historic historic) {
		return new HistoricDto(historic.getId(),historic.getPatientId(),historic.getNote());
	}
	
}
