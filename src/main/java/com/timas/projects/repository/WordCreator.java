package com.timas.projects.repository;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.timas.projects.annotations.Config;
import com.timas.projects.exeptions.InitException;
import com.timas.projects.game.world.World;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.net.URL;

@Log4j
public class WordCreator {

    private static String configFile;

    private static World PROTOTYPE_OF_WORLD;

    public WordCreator() {
        this.configFile = null;

        init();
    }

    @SneakyThrows
    private static World loadWorld(URL resource) {
        YAMLMapper yamlMapper = new YAMLMapper();
        World world;
        try {
            world = yamlMapper.readValue(resource, World.class);

        } catch (Exception e) {
            String message = String.format("Cannot find config file %s, for class %s", resource.getFile(), World.class);
            throw new InitException(message, e);
        }
        return world;
    }

    private void init() {
        if (configFile == null) {

            //default config
            Config config = World.class.getAnnotation(Config.class);
            PROTOTYPE_OF_WORLD = loadWorld(World.class.getClassLoader().getResource(config.filename()));
        }

    }

    public World getPrototypeOfWorld() {
        return PROTOTYPE_OF_WORLD;
    }


}
