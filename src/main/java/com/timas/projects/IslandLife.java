package com.timas.projects;

import com.timas.projects.config.Config;

import com.timas.projects.utils.ResourceFilesUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IslandLife implements AutoCloseable{

    final String config_file;
    Config config;


    public IslandLife(String configFile) throws Exception {
        config_file = configFile;
        config = new Config(config_file);
    }


    // get default config from root directory, or get default config from resources

    public void start()
    {
      log.info("Island Life starting...");

      //log.info("World size preparation ["+config.getSizeX()+" x "+config.getSizeY()+"]");
      //world = new World(config.getSizeX(),config.getSizeY());

      log.info("Populating the world with creatures...");


  /*    try
      {
          log.debug( ResourceFilesUtil.getFiles("src/main/resources/config/entity",".yaml") );
      }catch (Exception e)
      {
          log.error(e.getLocalizedMessage());
      }*/


    }

    @Override
    public void close() throws Exception {
        // close ALL :)
    }
}
