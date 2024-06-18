package com.timas.projects.lifecycle.events;

import com.timas.projects.config.Configuration;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import com.timas.projects.services.entity.ReproduceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;

@Log4j
@RequiredArgsConstructor
@Getter
public class WorldCreation extends Event implements EventOfTheWorld {

    final WorldModifier worldModifier;
    final ReproduceCoordinator coordinator;
    final Configuration configuration;

    @Override
    public void event() {
        worldModifier.update((coordinate, cell) -> {
            Collection<Entity> genList = coordinator.generate(configuration.getFactor_generate());
            cell.setValue(genList);
        });
    }

}
