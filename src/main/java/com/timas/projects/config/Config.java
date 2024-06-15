package com.timas.projects.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.timas.projects.game.ProbabilityEaten;
import com.timas.projects.game.World;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.services.config.YamlReaderConfigService;
import com.timas.projects.utils.ResourceFilesUtil;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.*;

@Log4j
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Config {
    final static String ENTITIES_PATH = "com.timas.projects.game.entity";
    final static String DEFAULT_EXT = ".yaml";
    final static String DEFAULT_CONFIG = "config" + DEFAULT_EXT;
    final static String DEFAULT_CONFIG_DIR = "src/main/resources/config/";
    final static String DEFAULT_CONFIG_IN_RESOURCES = DEFAULT_CONFIG_DIR+DEFAULT_CONFIG;
    final static String DEFAULT_CONFIG_ENTITIES_DIR = "src/main/resources/config/entity";
    final static String property_name_class = "name";

    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    final static Map<String, Class<?>> whiteListClassOfEntity = getWhiteListClassOfEntity();

    // World
    World world;

    // who eaten who
    ProbabilityEaten probabilityEaten;


    // Map of Map - chance of who eats who
    Map<Class<?>,Map<Class<?>,Integer> > probability_eaten;

    // Set of all possible entities in the world
    Set<?> entitySet;

    public Config() throws Exception {
      init(null);
    }
    public Config(String configFile) throws Exception {
      init(configFile);
    }

    void init(String configFile) throws Exception {

        log.info("Configuration started...");
        if(configFile==null) {
            log.info("Use default config:" + DEFAULT_CONFIG_IN_RESOURCES);
            configFile = DEFAULT_CONFIG_IN_RESOURCES;
        }
        else {
            log.info("Use config:" + configFile);
        }

        YamlReaderConfigService yamlReaderConfigService = new YamlReaderConfigService();

        Map<String,Object> configData = yamlReaderConfigService.readYamlFile(configFile);

        if( configData==null)
            throw new Exception("Config "+configFile+" read failure");

        //-------------------------------
        Map<String,Object> worldProperty = (Map<String, Object>) yamlReaderConfigService.getProperty(configData,"world");

        if (worldProperty.isEmpty())
            throw new Exception("Parameter <world> not found in config file "+configFile);

        world = yamlReaderConfigService.getValue(worldProperty, World.class);

        //-------------------------------
        Set<File> yaml_files_entity = ResourceFilesUtil.getFiles(DEFAULT_CONFIG_ENTITIES_DIR, DEFAULT_EXT);

        if (yaml_files_entity.isEmpty())
            throw new Exception("Config yaml files for entity not found");

        entitySet = new HashSet<>();

        for (File file : yaml_files_entity) {
            var data = yamlReaderConfigService.readYamlFile(file.getPath());

            String name = (String) data.get(property_name_class);

            if (name == null ) {
                log.error("skip " + file.getName() + ":the file is not valid");
                continue;
            }
            if (!whiteListClassOfEntity.containsKey(name)) {
                log.error("skip " + file.getName() + ": the file is not whitelisted");
                continue;
            }

            Class<?> clazz_entity = whiteListClassOfEntity.get(name);

            try
            {
                entitySet.add(yamlReaderConfigService.getValue(data,clazz_entity));
                log.error("serialization done: "+file.getName()+" -> "+clazz_entity.toString());
            }catch (Exception e)
            {
                log.error("deserialization error: "+file.getName()+" -> "+clazz_entity.toString());
            }
        }

        if (entitySet.isEmpty())
            throw new Exception("Entities serialization failed");

        log.debug("Entities serialization completed:"+entitySet);

        //-------------------------------
        Map<String,Map<String,Integer>> probabilityEatenProperties = (Map<String,Map<String,Integer>>) yamlReaderConfigService.getProperty(configData,"probability_eaten");
        log.debug(probabilityEatenProperties);
        if (probabilityEatenProperties.isEmpty())
            throw new Exception("parameter <probability_eaten> not found in config file "+configFile);
        probabilityEaten = new ProbabilityEaten(probabilityEatenProperties,whiteListClassOfEntity);
        log.debug("Probability eaten configure completed");

        //-------------------------------

        log.info("Configuration completed");
    }

    /**
     *  Получить все классы, в иерархии сущностей Entity
     * @return все возможные классы Entity
     */
    private static Map<String, Class<?>> getWhiteListClassOfEntity()
    {
        //Prepare whitelist in package
        Map<String, Class<?>> whiteList = new HashMap<>();
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages(ENTITIES_PATH)
                .scan()) {
            for (ClassInfo classInfo : scanResult
                    .getSubclasses(Entity.class)
                    .filter(classInfo -> !classInfo.isAbstract())
            ) {
                whiteList.put(classInfo.getSimpleName(), classInfo.loadClass());
            }
        }
        return whiteList;
    }

}
