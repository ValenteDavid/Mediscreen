package com.mediscreen.historic.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.mediscreen.historic.domain.Historic;

public class HistoricDto {

	@Id
	private Integer id;
	@NotNull
	private Integer patientId;
	@NotEmpty
	private String note;
	
	public HistoricDto() {
	}
	
	public HistoricDto(Integer id, @NotNull Integer patientId, @NotEmpty String note) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
