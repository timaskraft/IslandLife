package com.timas.projects.lifecycle.events;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.FoodCoordinator;
import com.timas.projects.services.entity.MoveCoordinator;
import com.timas.projects.services.entity.ReproduceCoordinator;
import com.timas.projects.utils.EntityUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Log4j
@RequiredArgsConstructor
@Getter
public class EventMovingFauna extends Event implements EventOfTheWorld<Long>{

    final WorldModifier worldModifier;
    final MoveCoordinator moveCoordinator;

    @Override
    public Long event() {
            AtomicLong alive_moving = new AtomicLong(0);

            //Список всех, кто походил за такт (speed)
            Set<Alive> moved = ConcurrentHashMap.newKeySet();
            // speed and move
            worldModifier.update((coordinate, cell) -> {

                Set<Alive> movd = moveCoordinator.moving(worldModifier,coordinate,cell.getValue());
                moved.addAll(movd);
                alive_moving.addAndGet(movd.size());

            });

            //reset speed
            //очищать текущую скорость нужно только после того, как полностью все походили.
            moved.forEach(e->{
                    e.setSpeed(0);
                });

            return alive_moving.longValue();
        }
}
