package com.timas.projects.services;

import com.timas.projects.model.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.ToString;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ToString
public class ModelsManager {
    private static final String RESOURCES_PATH = "/resources/config/";
    private static final Map<String, Entity> referenceInstances = new HashMap<>();

    public static void loadModels() {
       /*
        try {
            Set<String> yamlFiles =  new HashSet<>(); //getResourceFiles(RESOURCES_PATH);

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            for (String yamlFile : yamlFiles) {

                try (InputStream inputStream = ModelManager.class.getResourceAsStream(RESOURCES_PATH + yamlFile)) {

                    Entity instance = mapper.readValue(inputStream, Entity.class);

                    referenceInstances.put(yamlFile, instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}
