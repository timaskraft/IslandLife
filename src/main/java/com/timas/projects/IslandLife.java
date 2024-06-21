package com.timas.projects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.timas.projects.config.Configuration;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.game.world.Cell;
import com.timas.projects.game.world.World;
import com.timas.projects.lifecycle.LifeCycleManager;
import com.timas.projects.repository.EntityFactory;
import com.timas.projects.repository.RelationEatenCreator;
import com.timas.projects.repository.WordCreator;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.FoodCoordinator;
import com.timas.projects.services.entity.FoodService;
import com.timas.projects.services.entity.ReproduceCoordinator;
import com.timas.projects.services.entity.ReproduceService;
import com.timas.projects.services.random.RandomService;
import com.timas.projects.services.render.ConsoleRenderStrategy;
import com.timas.projects.services.render.RenderParam;
import com.timas.projects.services.render.RenderService;
import com.timas.projects.services.render.RenderStrategy;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.net.URI;
import java.util.Collection;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IslandLife implements AutoCloseable {

    final String configFile;
    Configuration config;

    public IslandLife(String configFile) throws Exception {

        this.configFile = configFile;
        config = loadConfigFromYaml(configFile);
    }

    // Строим мир, заполняем существами
    public void start() {

        World world = new WordCreator().getPrototypeOfWorld();
        /////////////////////////////////////////////////////////////////////////////

        RenderStrategy renderStrategy =null;
        if (config.isStatistics_console()) {
            renderStrategy = new ConsoleRenderStrategy();
        }

        if (renderStrategy==null)
            throw new IllegalArgumentException("Statistics rendering method is not selected");

        RenderService renderService = new RenderService(renderStrategy);

        log.info("Island Life simulate start...");

        LifeCycleManager manager = new LifeCycleManager(config,renderService,world);
        manager.startLifeCycle();

        log.info("Simulated stop.");


    }

    private Configuration loadConfigFromYaml(String config_file)
    {
        Configuration configuration = new Configuration();
        try {
            log.info("Read configuration: "+config_file);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);


            configuration = mapper.readValue( new File(System.getProperty("user.dir") + File.separator + config_file), Configuration.class);

            log.info("Use configuration: "+config_file);

            return configuration;
        }catch (Exception e)
        {
            log.error(e.getLocalizedMessage());
            log.error("Read "+config_file+" failure. Configuration set on default.");
        }
        return configuration;
    }


    @Override
    public void close() throws Exception {

        log.info("Island Life simulate done...");
        // close ALL :)
    }
}


/*

      EntityFactory entityFactory = new EntityFactory(config.getEntitiesDefault());

      log.info("Creating a landscape...");
      // TODO::вызвать фабрику с фильтром Inanimate - неживое ( поместить разные статичные объекты)

      log.info("Plant plants...");
      // вызвать фабрику с фильтром Flora
      Map<Class<? extends Entity>, Entity> flora = config.getEntitiesDefault(Flora.class);

      //садим первоначальные растения.
      flora.forEach((clazz,entity)->{
              for (int i = 0; i < entity.getMaxAmount(); i++) {

                  try {
                      Flora newEntity = (Flora) entityFactory.getNewEntity(clazz);

                      log.debug(newEntity.getName());

                  } catch (CloneNotSupportedException e) {
                      throw new RuntimeException(e);
                  }
              }
      });

      log.info("Populating the world with creatures...");

      Map<Class<? extends Entity>, Entity> fauna = config.getEntitiesDefault(Fauna.class);
      // вызвать фабрику с фильтром Fauna
        //садим первоначальные растения.
      fauna.forEach((clazz,entity)->{
            for (int i = 0; i < entity.getMaxAmount(); i++) {
                try {
                    Fauna newEntity = (Fauna) entityFactory.getNewEntity(clazz);
                    log.debug(newEntity.getName());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


      try
      {
       //   log.debug("Get test wolf:"+entityFactory.getTestWolf());
       //   log.debug("Get test wolf:"+entityFactory.getTestWolf());

      }catch (Exception e)
      {
          log.error(e.getLocalizedMessage());
      }
*/


  /*    try
      {
          log.debug( ResourceFilesUtil.getFiles("src/main/resources/config/entity",".yaml") );
      }catch (Exception e)
      {
          log.error(e.getLocalizedMessage());
      }*/
