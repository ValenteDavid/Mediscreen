package com.mediscreen.historic.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Historic")
public class Historic {

	@Id
	private Integer id;
	@Field(value = "patient_id")
	private Integer patientId;
	@Field(value = "note")
	private String note;

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
		return "Historic [id=" + id + ", patientId=" + patientId + ", note=" + note + "]";
	}

}
