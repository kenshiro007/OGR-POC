package fr.demo.metier.service.property;

import java.util.Map;

public interface PropertyService {

  String getProperty(String key);

  String getService(String serviceKey);

  Map<String, String> getProperties();
}
