package com.timas.projects.services.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.Map;

@Log4j
public class YamlReaderConfigService {
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());


    public YamlReaderConfigService()
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Map<String, Object> readYamlFile(String name_yaml)
    {
        try {
                return objectMapper.readValue(new File(name_yaml), Map.class);

            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        return null;
    }

    public Object getProperty(Map<String, Object> yamlMap,String property)
    {
        return yamlMap.get(property);
    }

    public <T> T getValue(Map<String, Object> yamlMap,Class<?> clazz)
    {
        T obj = (T) objectMapper.convertValue(yamlMap,clazz);

        return obj;
    }



}
