package com.timas.projects.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.timas.projects.model.Entity;
import com.timas.projects.utils.ResourceFilesUtil;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Config {

    final static String DEFAULT_EXT = ".yaml";
    final static String DEFAULT_CONFIG = "config" + DEFAULT_EXT;
    final static String DEFAULT_CONFIG_IN_RESOURCES = DEFAULT_CONFIG;
    final static String DEFAULT_CONFIG_ENTITIES_DIR = "src/main/resources/config/entity";
    final static String property_name_class = "name";

    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    final String configFile;


    int sizeX;
    int sizeY;

    Set<?> entitySet;
    Map<String, Class<?>> whiteList;

    public Config(String configFile) {
        if (configFile != null) {
            this.configFile = configFile;
            log.info("Use config:" + configFile);
        } else {
            //TODO:: <read config from yaml> -> get config from root dir else get config from resources

            this.configFile = DEFAULT_CONFIG_IN_RESOURCES;
            log.info("Use default config from resource:" + this.configFile);
        }

        init();
    }

    void init() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // default settings - read config.yaml
        sizeX = 10;
        sizeY = 10;
        //--------

        //Prepare whitelist in package
        whiteList = new HashMap<>();
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("com.timas.projects.model")
                .scan()) {
            for (ClassInfo classInfo : scanResult
                                       .getSubclasses(Entity.class)
                                       .filter(classInfo -> !classInfo.isAbstract())
                ) {
                    whiteList.put(classInfo.getSimpleName(), classInfo.loadClass());
                  }
        }

        log.debug("whiteList class:"+whiteList.toString());

        // read all entity to set
        entitySet = new HashSet<>();

        log.debug("Reading entities config yaml...");
        try {
            Set<Path> entities_files = ResourceFilesUtil.getFiles(DEFAULT_CONFIG_ENTITIES_DIR, DEFAULT_EXT);
            entities_files.forEach(entity -> {
                log.debug("found yaml file:"+entity.toString());
                try {
                    entitySet.add(readOnlyValidEntity(entity.toFile()));
                } catch (Exception e) {
                   log.debug(e.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        if (entitySet.isEmpty())
            throw new RuntimeException("Can't run IcelandLife without Entity");

        log.debug("deserialize " + entitySet.size() + " objects:"+entitySet.toString());

        entitySet.forEach(e->{
            log.debug(e.toString());
        });




    }

    // todo:: refactor readOnlyValidEntity - to service ???
    <T> T readOnlyValidEntity(File file) throws Exception {

        FileInputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        // get property "name" - its only One check...fine
        String name = (String) data.get(property_name_class);
        if (name != null) {
            // check in whiteList entity class
            if (whiteList.containsKey(name)) {

                log.debug("deserialize " + file.getName() + " to " + whiteList.get(name));
                Class<T> clazz_entity = (Class<T>) whiteList.get(name);

                return mapper.readValue(file, clazz_entity );

            } else
                throw new Exception("unknown class " + name + " in yaml file " + file.getName());
        } else
            throw new Exception("skip " + file.getName() + ": file not valid");


    }
}
