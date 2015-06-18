package com.philips.xucdm;

public interface Serializer {
  void serialize(Long since, String country, String language) throws Exception;
}
