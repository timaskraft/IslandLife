package com.timas.projects.lifecycle.events;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.entity.ReproduceCoordinator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Log4j
@RequiredArgsConstructor
@Getter
public class EventSpawnFlora extends Event implements EventOfTheWorld<Long>{

    /* TODO:: можно было бы генерировать сущность воды, и рандомно расбрасывать по полю рандомное
              количество, далее уже обработать количество води и взаимосвязь между животными и растениями.
              НО пока - идет дождь, и каждая сущность растения получает шанс репродукции.
    */

    final WorldModifier worldModifier;
    final ReproduceCoordinator reproduceCoordinator;

    @Override
    public Long event() {

        AtomicLong flora_grow = new AtomicLong(0);

        /* список всей флоры для рандома */
        List<Flora> possible_flora =  reproduceCoordinator.getReproduceService()
                                                          .getEntityFactory()
                                                          .getPrototypesEntities(Flora.class)
                                                          .stream().map(Flora.class::cast).toList();

        if (possible_flora.isEmpty())
        {
            log.error("Possible flora not found!");
            return 0L;
        }


        worldModifier.update((coordinate, cell) -> {

            //* сюда будем добавлять появившиеся растения */
            Set<Entity> growFlora = ConcurrentHashMap.newKeySet();

            //способ роста - рандом на клетке.
            //получить все возможные растения possible_flora;
            Flora poss_flora = possible_flora.get( reproduceCoordinator.getRandomService()
                                                    .nextInt(0,possible_flora.size() -1)
                                                 );
            // сколько уже в клетке таких растений
            int plants_in  = (int) cell.getValue()
                                      .parallelStream()
                                      .filter(p->p.getClass().equals(poss_flora.getClass()))
                                      .count();
            // если больше чем положено, выходим
            if( poss_flora.getMaxAmount() <= plants_in ) return;
            // сколько попытаться сгенерировать таких растений с шансом генерации из конфига, не более чем возможно
           // long how_many = reproduceCoordinator.getRandomService().nextInt(plants_in  ,poss_flora.getMaxAmount() );
          //  for (int i = 0; i < how_many; i++) {
            Entity entity = reproduceCoordinator.bornWithDefaultChanceSpawn( poss_flora);
                if (entity!=null) {
                    growFlora.add(entity);
                    flora_grow.incrementAndGet();
                }
            // }
            if(!growFlora.isEmpty())
                cell.getValue().addAll(growFlora);

        });


        return flora_grow.longValue();

    }
}


/*
            //альтернативный способ роста - деление растений на клетке.
            cell.getValue()
                    .parallelStream()
                    .filter(Flora.class::isInstance)
                    .filter(e->((Flora) e).getLive()>0) //только по живым растениям
                    .forEach(e->{
Entity entity = reproduceCoordinator.born(e);
                        if (entity!=null) {

long wild_grow  = growFlora.size();
long plants_in  = cell.getValue()
        .parallelStream()
        .filter(p->p.getClass().equals(e.getClass()))
        .count();

// если уже рожденные + те которые растут в клетке < максимально возможных, то растим еще!
                            if( wild_grow + plants_in < e.getMaxAmount()  )
        {
        growFlora.add(entity);
                                flora_grow.incrementAndGet();
                            };
                                    }
                                    });

                                    cell.getValue().addAll(growFlora);

 */
