package com.timas.projects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.timas.projects.config.Configuration;
import com.timas.projects.game.world.World;
import com.timas.projects.lifecycle.LifeCycleManager;
import com.timas.projects.repository.WordCreator;
import com.timas.projects.services.render.ConsoleRenderStrategy;
import com.timas.projects.services.render.RenderService;
import com.timas.projects.services.render.RenderStrategy;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.io.File;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IslandLife implements AutoCloseable {

    Configuration config;

    public IslandLife(String configFile) throws Exception {

        config = loadConfigFromYaml(configFile);
    }

    // Строим мир, заполняем существами
    public void start() {

        World world = new WordCreator().getPrototypeOfWorld();
        /////////////////////////////////////////////////////////////////////////////

        RenderStrategy renderStrategy = null;
        if (config.isStatistics_console()) {
            renderStrategy = new ConsoleRenderStrategy();
        }

        if (renderStrategy == null)
            throw new IllegalArgumentException("Statistics rendering method is not selected");

        RenderService renderService = new RenderService(renderStrategy);

        log.info("Island Life simulate start...");

        LifeCycleManager manager = new LifeCycleManager(config, renderService, world);
        manager.startLifeCycle();

    }

    private Configuration loadConfigFromYaml(String config_file) {
        Configuration configuration = new Configuration();
        try {
            log.info("Read configuration: " + config_file);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);


            configuration = mapper.readValue(new File(System.getProperty("user.dir") + File.separator + config_file), Configuration.class);

            log.info("Use configuration: " + config_file);

            return configuration;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error("Read " + config_file + " failure. Configuration set on default.");
        }
        return configuration;
    }


    @Override
    public void close() throws Exception {

        // close ALL :)
    }
}

