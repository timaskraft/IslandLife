package com.timas.projects.lifecycle.tasks;

import com.timas.projects.config.Configuration;
import com.timas.projects.lifecycle.events.EventFeeding;
import com.timas.projects.lifecycle.events.EventMovingFauna;
import com.timas.projects.lifecycle.events.EventSpawnFauna;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.FoodCoordinator;
import com.timas.projects.services.entity.MoveCoordinator;
import com.timas.projects.services.entity.ReproduceCoordinator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class RoutineTask extends LifeTask {
    final WorldModifier worldModifier;
    final FoodCoordinator foodCoordinator;
    final ReproduceCoordinator reproduceCoordinator;
    final MoveCoordinator moveCoordinator;
    final Configuration configuration;


    @Override
    public void run() {
        count_tick++;
        try {

            Long dead_eating = new EventFeeding(worldModifier, foodCoordinator).event();

            Long spawning = new EventSpawnFauna(worldModifier, reproduceCoordinator).event();

            Long moving = new EventMovingFauna(worldModifier, moveCoordinator).event();

            log.info("ate : " + dead_eating + " were born :" + spawning + " moved:" + moving);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
