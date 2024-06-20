package com.timas.projects.lifecycle;

import com.timas.projects.config.Configuration;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.game.world.World;
import com.timas.projects.lifecycle.events.EventOfTheWorld;
import com.timas.projects.lifecycle.events.WorldCreation;
import com.timas.projects.lifecycle.tasks.EventTask;
import com.timas.projects.lifecycle.tasks.RenderTask;
import com.timas.projects.lifecycle.tasks.RoutineTask;
import com.timas.projects.repository.EntityFactory;
import com.timas.projects.repository.RelationEatenCreator;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.*;
import com.timas.projects.services.random.RandomService;
import com.timas.projects.services.render.RenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Log4j
@RequiredArgsConstructor
public class LifeCycleManager {

    final Configuration configuration;
    final RenderService renderService;
    final World world;

    /////////////////////////////////////////////////////////
    private final RandomService randomService = new RandomService();
    /*Инициализация Фабрики сущностей */
    private final EntityFactory entityFactory = new EntityFactory();

    /*Взаимосвязи между сущностями, кто кого кушает */
    private final RelationEaten relationEaten = new RelationEatenCreator(entityFactory.getTypesOfEntities()).getRelationEaten();
    /* сервис репродукции */
    private final ReproduceService reproduceService = new ReproduceService(entityFactory);
    private final ReproduceCoordinator reproduceCoordinator = new ReproduceCoordinator(randomService, reproduceService);

    /* сервис питания */
    private final FoodService foodService = new FoodService();
    private final FoodCoordinator foodCoordinator = new FoodCoordinator(randomService,foodService,relationEaten);
    ////////////////////////////////////////////////////////////////////////////////////////////
    /* сервис движения */
    private final MoveService moveService = new MoveService();
    private final MoveCoordinator moveCoordinator = new MoveCoordinator(randomService,moveService);


    /* пул потоков */
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);



    public void startLifeCycle() {

        /* инит модификатора мира */
        WorldModifier worldModifier = new WorldModifier(world);
        worldModifier.init(configuration.getSizeX(),configuration.getSizeY());

        /* запускаем евент заселение мира */
        new WorldCreation(worldModifier,reproduceCoordinator,configuration).event();

        /* Разделим лайф цикл на несколько блоков
          1 - рутина (покушали - размножились - походили)
          2 - ивенты (рост растений, катаклизм ( пожар U+1F525, землетрясение, нападение раптора 🦖) )
          3 - рендер мира каждые n сек, и вывод статистики
         */

        // Блок 1 - Рутина: покушали - размножились - походили

        RoutineTask routineTask = new RoutineTask(worldModifier,foodCoordinator,reproduceCoordinator,moveCoordinator,configuration);

        executorService.scheduleWithFixedDelay(routineTask, 0, configuration.getPause_routine(), TimeUnit.MILLISECONDS);


        if (configuration.getEvent_timeout()>0) {
            // Блок 2 - Ивенты: рост растений, катаклизмы

            EventTask eventsTask = new EventTask(worldModifier,reproduceCoordinator,configuration);

            executorService.scheduleWithFixedDelay(eventsTask, 0, configuration.getEvent_timeout(), TimeUnit.SECONDS);
        }


        if (configuration.getStatistics_timeout()>0)
        {
            // Блок 3 - Рендер мира и вывод статистики каждые n секунд
            RenderTask renderTask = new RenderTask(renderService,world);

            executorService.scheduleWithFixedDelay(renderTask, 0, configuration.getStatistics_timeout(), TimeUnit.SECONDS);
        }

    }

    public void stopLifeCycle() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
