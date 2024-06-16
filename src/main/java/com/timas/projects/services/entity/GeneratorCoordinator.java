package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.repository.EntityFactory;
import com.timas.projects.services.random.RandomGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Log4j
@RequiredArgsConstructor
public class GeneratorCoordinator {

    private final EntityFactory entityFactory;
    private final RandomGeneratorService randomGeneratorService;

    public Collection<Entity> generate(Collection<Entity> possible_entities)
    {

        // прокрутить список, и в соответствии generate_factor в randomGeneratorService
        // запустить случайную генерацию вида от 0 до maxAmount вида.

        List<Entity> newList = new ArrayList<>();

        possible_entities.forEach(entity -> {

            // случайно возможное количество вида. (пока уменьшим в 3 раза)
            int count_for_gen =   randomGeneratorService.get(entity.getChanceReproduce()) / 10;
            for (int i = 0; i < count_for_gen; i++) {
                if (randomGeneratorService.get(100)<50 )
                {
                    try {
                        Entity cloneEntity = entityFactory.cloneEntity(entity);

                        if(cloneEntity instanceof Fauna)
                        {
                            ((Fauna) cloneEntity).setMale(randomGeneratorService.get(100)<50);
                        }

                        newList.add(cloneEntity);
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        return newList;
    }

}
