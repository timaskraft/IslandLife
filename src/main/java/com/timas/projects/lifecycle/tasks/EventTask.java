package com.timas.projects.lifecycle.tasks;

import com.timas.projects.config.Configuration;
import com.timas.projects.lifecycle.events.EventSpawnFlora;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class EventTask extends LifeTask{
    final WorldModifier worldModifier;
    final ReproduceCoordinator coordinator;
    final Configuration configuration;

    // хотелось бы сюда передавать список возможных эвентов
    // но пока что - это только эвент дождь
    //    private Map<Class<? extends Event>,Integer > eventMap;

    @Override
    public void run() {

        count_tick++;

        // Event RAIN
        // Если дождь прошел, то растения появляются.
        if(coordinator.getRandomService().takeChance(configuration.getChance_rain())) {
           long flora_grow  =  new EventSpawnFlora(worldModifier,coordinator).event();
           log.info("It rained \uD83C\uDF27 and "+flora_grow+" \uD83C\uDF31 plants grew");
        }


    }
}
