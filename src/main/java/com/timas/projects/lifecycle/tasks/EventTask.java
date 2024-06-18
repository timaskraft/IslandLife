package com.timas.projects.lifecycle.tasks;

import com.timas.projects.config.Configuration;
import com.timas.projects.lifecycle.events.Event;
import com.timas.projects.lifecycle.events.FloraGrow;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Map;

@Log4j
@RequiredArgsConstructor
public class EventTask implements Runnable{
    final WorldModifier worldModifier;
    final ReproduceCoordinator coordinator;
    final Configuration configuration;


    private long count_event;

    private Map<Class<? extends Event>,Integer > eventMap;

    @Override
    public void run() {

        count_event++;

        log.debug("event task "+count_event);
        // Event RAIN
        // Если дождь прошел, то растения появляются.
        if(coordinator.getRandomService().takeChance(configuration.getChance_rain())) {
            log.info("Event:It's raining, the plants are growing");
            new FloraGrow(worldModifier,coordinator).event();
        }


    }
}
