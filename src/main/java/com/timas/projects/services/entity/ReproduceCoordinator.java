package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.services.random.RandomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Координатор репродукция сущностей. Кого, и как репродуцировать в переданном списке.
 */
@Log4j
@RequiredArgsConstructor
public class ReproduceCoordinator {

    @Getter
    private final RandomService randomService;

    @Getter
    private final ReproduceService reproduceService;


    public Collection<Entity> generate() {
        return generate(1);
    }

    public Collection<Entity> generate(int factor_generate) {
        return generate(reproduceService.getEntityFactory().getPrototypesEntities(Alive.class), factor_generate);
    }

    public Collection<Entity> generate(Class<? extends Entity> aClass) {
        return generate(reproduceService.getEntityFactory().getPrototypesEntities(aClass), 1);
    }

    public Collection<Entity> generate(Collection<Entity> entityCollection, int factor_generate) {
        Set<Entity> newList = ConcurrentHashMap.newKeySet();

        entityCollection.forEach(entity -> {

            Entity bornEntity = bornWithDefaultChanceSpawn(entity);
            if (bornEntity != null)
                newList.add(bornEntity);

        });

        return newList;
    }

    public Entity born(Entity who) {
        try {
            Entity bornEntity = reproduceService.reproduce(who);
            if (bornEntity instanceof Fauna) {
                ((Fauna) bornEntity).setMale(randomService.nextBoolean());
            }

            return bornEntity;

        } catch (CloneNotSupportedException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public Entity bornWithDefaultChanceSpawn(Entity who) {
        if (randomService.takeChance(who.getChanceSpawn()))
            return born(who);
        return null;
    }

}
