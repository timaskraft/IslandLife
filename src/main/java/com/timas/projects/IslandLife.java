package com.timas.projects;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.game.world.World;
import com.timas.projects.repository.EntityFactory;
import com.timas.projects.repository.RelationEatenCreator;
import com.timas.projects.repository.WordCreator;
import com.timas.projects.services.entity.GeneratorCoordinator;
import com.timas.projects.services.random.RandomGeneratorService;
import com.timas.projects.services.random.RandomService;
import com.timas.projects.services.render.ConsoleRenderStrategy;
import com.timas.projects.services.render.RenderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.List;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IslandLife implements AutoCloseable {

    final String configFile;
    // Config config;


    public IslandLife(String configFile) throws Exception {
        this.configFile = configFile;
        //config = new Config(config_file);
    }


    // Строим мир, заполняем существами
    public void start() {

        log.info("Island Life configure...");

        //TODO:: пока без логики передачи конфигурации мира из внешнего файла, т.е. конфиг берем из аннотации config из ресурсов.

        WordCreator wordCreator = new WordCreator();

        World world = wordCreator.getPrototypeOfWorld();

        world = world.init();

        log.debug(world);

        //world.printWorldToConsole();


        /*Фабрика сущностей */
        EntityFactory entityFactory = new EntityFactory();

        entityFactory.getPrototypesEntities().forEach(entity -> {
            log.debug(entity);
        });


        /*Взаимосвязи между сущностями, кто кого кушает */
        RelationEaten relationEaten = new RelationEatenCreator(entityFactory.getTypesOfEntities()).getRelationEaten();

        log.debug(relationEaten);

        log.info("Island Life simulate start...");

        RandomService randomService = new RandomService();


        RenderService renderService = new RenderService();
        renderService.setRenderStrategy(new ConsoleRenderStrategy());

        RandomGeneratorService randomGeneratorService = new RandomGeneratorService(randomService);

        GeneratorCoordinator generatorCoordinator = new GeneratorCoordinator(entityFactory, randomGeneratorService);

        // для одной клетки
        log.info("Generate field...");
        for (int y = 0; y < world.getGameField().getSizeY(); y++) {
            for (int x = 0; x < world.getGameField().getSizeX(); x++) {

                Collection<Entity> genList = generatorCoordinator.generate(entityFactory.getPrototypesEntities());
                world.getGameField().setCollectionToCell(x,y,genList);

            }
        }


        renderService.render(world.getGameField());





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


    }


    @Override
    public void close() throws Exception {

        log.info("Island Life simulate done...");
        // close ALL :)
    }
}
