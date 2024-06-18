package com.timas.projects.lifecycle.events;

import com.timas.projects.config.Configuration;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import com.timas.projects.services.random.RandomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Log4j
@RequiredArgsConstructor
@Getter
public class FloraGrow extends Event implements EventOfTheWorld{
    final WorldModifier worldModifier;
    final ReproduceCoordinator coordinator;

    @Override
    public void event() {

        AtomicLong flora_grow = new AtomicLong(0);

        worldModifier.update((coordinate, cell) -> {

            log.debug("size before:"+cell.getValue().size());
            //Collection<Entity> allEntity = new ArrayList<>(cell.getValue());

            /* сюда будем добавлять появившиеся растения */
            Collection<Entity> growFlora = new ArrayList<>();

            Collection<Entity> flora = cell.getValue().parallelStream().filter(Flora.class::isInstance).toList();

            // получить всех наследников класса Flora, сгруппировать, получить сумму каждого объекта в клетке
            Map<Class<? extends Flora>,Long> collectionFlora = flora.parallelStream()
                                                                     .map(Flora.class::cast)
                                                                     .collect(Collectors.groupingBy(Flora::getClass,
                                                                                  Collectors.counting()));
            // Получить список максимально возможных в коллекции
            // и взять свойство "максимальное количество этого вида" возьмем из первой записи этого объекта этого списка
            // Идем по списку флоры,
            flora.forEach(entity -> {
                // каждому растению даем шанс размножиться.
                if (coordinator.getRandomService().takeChance(entity.getChanceReproduce())) {
                    //количество в списке конкретной сущности
                    long quantity = collectionFlora.get(entity.getClass());
                    //сгенерированные в этом цикле
                    long grow     = growFlora.size();
                    //максимально возможное количество в коллекции
                    long possible_max_count = entity.getMaxAmount();

                    if( possible_max_count > quantity + grow  )
                    {
                        flora_grow.incrementAndGet();
                        growFlora.add(coordinator.born(entity));
                       // log.debug(coordinate.x() + ":" + coordinate.y() + " = " + entity.getName() + " " + quantity + " " + possible_max_count);
                    }
                }

            });

            cell.getValue().addAll(growFlora);
            //cell.setValue(allEntity);

            log.debug("size after:"+cell.getValue().size());
        });
        log.debug("grow all"+flora_grow);



    }
}

