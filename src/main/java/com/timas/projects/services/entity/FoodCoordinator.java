package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.services.random.RandomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
@RequiredArgsConstructor
public class FoodCoordinator {
    final RandomService randomService;
    final FoodService reproduceService;
    final RelationEaten relationEaten;

    //TODO:: требуется рефакторинг, но неясно как с фудсервисом быть. пока удаляю прямо здесь

    /* лучше ли новую коллекцию возвращать - это предпочтительно, если юзаем потоки.*/
    public Collection<Entity> meal(Collection<Entity> entities) {

        Collection<Entity> newEntities = new ArrayList<>(entities);
        Set<Entity> toRemove = new HashSet<>();

       // log.debug("Всего :"+newEntities.size());
        // итератор только по тем, кто способен есть, а это Fauna
        Iterator<Entity> it = newEntities
                                    .parallelStream()
                                    .filter(entity ->
                                            Fauna.class.isAssignableFrom(entity.getClass())
                                    ).iterator();

        while (it.hasNext()) {

            Entity current = it.next();

            // получили возможный список тех, кого гипотетически можем съесть
            Map<Class<? extends Entity>, Integer> possibleEaten = relationEaten.getEatenMap(current.getClass());
            if (!possibleEaten.isEmpty()) {

                // Получаем первого претендента на съедание
            Optional<Entity> toBeEaten = newEntities.parallelStream()
                                            .filter(e -> possibleEaten.containsKey(e.getClass())
                                                    &&  !possibleEaten.containsKey(current.getClass())
                                                    &&  !toRemove.contains(e)
                                                   )
                                            .findFirst();

            toBeEaten.ifPresentOrElse(toBe -> {
                          int chance = relationEaten.getEaten(current.getClass(),toBe.getClass());
                         // log.debug(current.getName()+" хочет съесть "+toBe.getName()+" с шансом "+chance);
                          if (randomService.takeChance(chance ) )
                          {
                             // log.debug(current.getName()+" съедает "+toBe.getName());
                              toRemove.add(toBe); // Помечаем к удалению.
                          }else
                          {
                            //  log.debug(current.getName()+" не получилось съесть "+toBe.getName()+" с шансом "+chance);
                          }
                        },
                    ()->{
                      //  log.debug(current.getName()+" остался голодным");
                    }
                    );
            }
        }
        newEntities.removeAll(toRemove);

        //log.debug("Осталось в клетке "+newEntities.size());

        return newEntities;
    }


}
