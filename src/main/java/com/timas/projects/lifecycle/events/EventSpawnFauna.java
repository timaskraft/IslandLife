package com.timas.projects.lifecycle.events;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import com.timas.projects.utils.EntityUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Log4j
@RequiredArgsConstructor
@Getter
public class EventSpawnFauna extends Event implements EventOfTheWorld<Long> {

    final WorldModifier worldModifier;
    final ReproduceCoordinator reproduceCoordinator;


    @Override
    public Long event() {
        AtomicLong alive_born = new AtomicLong(0);
        worldModifier.update((coordinate, cell) -> {

            Set<Entity> bornFauna = ConcurrentHashMap.newKeySet();

            // сгруппировать по видам существ,и получить их количество
            Map<Class<? extends Entity>, Long> fauna_in_cell = EntityUtil.groupAndCountEntities(Fauna.class, cell.getValue());

            fauna_in_cell.forEach((aClassEntity, all_entity) -> {

                Entity prototypeEntity = reproduceCoordinator.getReproduceService()
                        .getEntityFactory()
                        .getPrototypeEntity(aClassEntity);

                long max_entity = prototypeEntity.getMaxAmount();

                if (all_entity < max_entity) {
                    // количество Male, количество Female (как разница между общим количеством и male)
                    long count_male = EntityUtil.getSetEntitiesOfClass(Fauna.class, cell.getValue())
                            .parallelStream()
                            .map(Fauna.class::cast)
                            .filter(aClassEntity::isInstance)
                            .filter(Fauna::isMale)
                            .count();

                    long pair = all_entity - Math.max(count_male, all_entity - count_male);

                    long borned = 0;

                    for (int i = 0; i < pair; i++) {

                        if (all_entity + borned >= max_entity) break;

                        Entity newEntity = reproduceCoordinator.bornWithDefaultChanceSpawn(prototypeEntity);

                        if (newEntity != null) {
                            bornFauna.add(newEntity);
                            alive_born.incrementAndGet();
                            borned++;
                        }
                    }
                }
            });


            if (!bornFauna.isEmpty())
                cell.getValue().addAll(bornFauna);

        });

        return alive_born.longValue();
    }
}
