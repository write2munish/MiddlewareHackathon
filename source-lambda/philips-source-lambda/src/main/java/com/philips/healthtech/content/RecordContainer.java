package com.philips.healthtech.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecordContainer implements Serializable {
	
	List<Record> records = new ArrayList<Record>();
	
	@JsonProperty("Records")
	public List<Record> getRecords() {
		return records;
	}
	
	public void setRecords(List<Record> records) {
		this.records = records;
	}

}
