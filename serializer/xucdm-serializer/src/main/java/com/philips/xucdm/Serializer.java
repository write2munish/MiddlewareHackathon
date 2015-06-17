package com.philips.xucdm;

public interface Serializer {
  String serialize(Long since, String country, String language) throws Exception;
}
