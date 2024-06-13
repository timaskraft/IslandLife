package com.timas.projects.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

@Log4j
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Config {

    public final static String DEFAULT_CONFIG = "config.yaml";
    public final static String DEFAULT_CONFIG_IN_RESOURCES = DEFAULT_CONFIG;

    final String configFile;

    int sizeX;
    int sizeY;

    public Config(String configFile) {
        if (configFile!=null)
        {
            this.configFile = configFile;
            log.info("Use config:"+configFile);
        }else {
            //TODO:: <read config from yaml> -> get config from root dir else get config from resources

            this.configFile = DEFAULT_CONFIG_IN_RESOURCES;
            log.info("Use default config from resource:"+ this.configFile);
        }

        init();
    }

    private void init()
    {
        // default
        sizeX = 10;
        sizeY = 10;
        //--------
    }
}
