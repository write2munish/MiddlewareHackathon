package com.philips;

import java.sql.Timestamp;

public class ChangeSet {
  String id;
  String type;
  String country;
  String language;
  Timestamp lastmodified;
  Long sequencenumber;
  public ChangeSet() {
  }
  public ChangeSet(String id, String type, String country, String language, Timestamp lastmodified, Long sequencenumber) {
    this.id = id;
    this.type = type;
    this.country = country;
    this.language = language;
    this.lastmodified = lastmodified;
    this.sequencenumber = sequencenumber;
  }
  
}
