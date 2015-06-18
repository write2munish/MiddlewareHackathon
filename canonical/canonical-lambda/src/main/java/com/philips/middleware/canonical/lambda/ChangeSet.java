package com.philips.middleware.canonical.lambda;

import java.sql.Timestamp;

public class ChangeSet {

	enum Type {
		PRODUCT("product"), FEATURE("feature"), UNIT("unit");

		private final String name;       

	    private Type(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }


	}

	Long sno;
	String id;
	String type;
	String country;
	String language;
	Timestamp lastmodified;
	Long sequencenumber;

	public ChangeSet() {
	}

	public ChangeSet(String id, Type type, String country, String language,
			Timestamp lastmodified, Long sequencenumber) {
		this.id = id;
		this.type = type.toString();
		this.country = country;
		this.language = language;
		this.lastmodified = lastmodified;
		this.sequencenumber = sequencenumber;
	}

	public Long getSno() {
		return sno;
	}

	public void setSno(Long sno) {
		this.sno = sno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type.toString();
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Timestamp getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Timestamp lastmodified) {
		this.lastmodified = lastmodified;
	}

	public Long getSequencenumber() {
		return sequencenumber;
	}

	public void setSequencenumber(Long sequencenumber) {
		this.sequencenumber = sequencenumber;
	}

}
