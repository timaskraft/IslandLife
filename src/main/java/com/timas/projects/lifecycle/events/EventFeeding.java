package com.timas.projects.lifecycle.events;


import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.FoodCoordinator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.atomic.AtomicLong;

@Log4j
@RequiredArgsConstructor
@Getter
public class EventFeeding extends Event implements EventOfTheWorld<Long>{

    /* Евент кормежки тех кто может есть*/

    final WorldModifier worldModifier;
    final FoodCoordinator foodCoordinator;

    @Override
    public Long event() {
        AtomicLong alive_is_dead_eating = new AtomicLong(0);
        worldModifier.update((coordinate, cell) -> {
            int dead_in_cell = foodCoordinator.meal( cell.getValue() );
            alive_is_dead_eating.addAndGet( dead_in_cell );
        });

        return alive_is_dead_eating.longValue();
    }
}
