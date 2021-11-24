package com.mediscreen.historic.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "historic")
public class Historic {

	@Id
	private String id;
	@Field(value = "patient_id")
	private Integer patient_id;
	@Field(value = "note")
	private String note;

	public Historic() {
		super();
	}

	public Historic(String id, Integer patientId, String note) {
		super();
		this.id = id;
		this.patient_id = patientId;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patient_id;
	}

	public void setPatientId(Integer patientId) {
		this.patient_id = patientId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Historic [id=" + id + ", patientId=" + patient_id + ", note=" + note + "]";
	}

}
