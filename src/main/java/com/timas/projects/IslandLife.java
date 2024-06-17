package com.timas.projects;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.game.world.World;
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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.util.Collection;

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

        /* Фактор генерации, уменьшает в n раз первичные популяции */
        int factor_generate = 20;
        /* максимальное количество циклов */
        long max_tick = 10;

        ////////////////////////////////////////////////////////////

        /* Инициализация мира */
        World world = new WordCreator().getPrototypeOfWorld();

        WorldModifier worldModifier = new WorldModifier(world);
        worldModifier.init();

        log.debug(world);

        /*Инициализация Фабрики сущностей */
        EntityFactory entityFactory = new EntityFactory();

        /*Взаимосвязи между сущностями, кто кого кушает */
        RelationEaten relationEaten = new RelationEatenCreator(entityFactory.getTypesOfEntities()).getRelationEaten();

        log.debug(relationEaten);

        RandomService randomService = new RandomService();

        RenderService renderService = new RenderService();
        renderService.setRenderStrategy(new ConsoleRenderStrategy());


        ReproduceService reproduceService = new ReproduceService(entityFactory);
        ReproduceCoordinator reproduceCoordinator = new ReproduceCoordinator(randomService, reproduceService);

        FoodService foodService = new FoodService();
        FoodCoordinator foodCoordinator = new FoodCoordinator(randomService,foodService,relationEaten);


        // Первоначальная генерация.
        worldModifier.update((coordinate, cell) -> {
            Collection<Entity> genList = reproduceCoordinator.generate(factor_generate);
            cell.setValue(genList);
        });


        long current_tick = 0;
        RenderParam renderParam = new RenderParam();

        log.info("Island Life simulate start...");
        while(true)
        {
            current_tick++;

            renderParam.setTick(current_tick);
            renderParam.setField(world.getGameField());

            renderService.render(renderParam);

            if (current_tick == max_tick)
            {
                log.info("maximum tick reached.");
                break;
            }
        }

        log.info("Simulated stop.");



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
