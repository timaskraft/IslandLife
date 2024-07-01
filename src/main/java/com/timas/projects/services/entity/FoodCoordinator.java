package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.services.random.RandomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
@RequiredArgsConstructor
public class FoodCoordinator {
    final RandomService randomService;
    final FoodService foodService;

    @Getter
    final RelationEaten relationEaten;

    public Integer meal(Collection<Entity> alives) {

        /* коллекция - в которой кто-то кого-то есть в соответствии с RelationEaten.*/
        /* У съеденных сбрасывается жизнь в 0 - становиться мертвым */
        /* вернем количество съеденных */

        Set<Alive> deads = ConcurrentHashMap.newKeySet();

        /* только живые, только голодные*/
        Iterator<Alive> it = alives
                .parallelStream()
                .filter(Alive.class::isInstance)
                .map(Alive.class::cast)
                .filter(e -> ((e.getFood() < e.getMaxFood()) & e.getLive() > 0))
                .iterator();
        while (it.hasNext()) {

            Alive who = it.next();

            // получили возможный список тех, кого гипотетически можем съесть
            Map<Class<? extends Alive>, Integer> possibleEaten = relationEaten.getEatenMap(who.getClass());

            if (!possibleEaten.isEmpty()) {
                // Получаем первого претендента на съедание
                // проверяем, есть ли оно во взаимоотношениях,
                // проверяем, то что это не сородич (это отбросит съедание самого себя в том числе). нам каннибалы не нужны. пока что :)
                // проверям, что оно живое. мертвичину в план не входит есть. пока что :)
                Optional<Alive> toBeEaten = alives.parallelStream()
                        .filter(Alive.class::isInstance)
                        .map(Alive.class::cast)
                        .filter(e -> possibleEaten.containsKey(e.getClass())
                                && !possibleEaten.containsKey(who.getClass())
                                && e.getLive() > 0
                        )
                        .findFirst();

                toBeEaten.ifPresent(toBe -> {
                            int chance = relationEaten.getEaten(who.getClass(), toBe.getClass());
                            if (randomService.takeChance(chance)) {
                                foodService.eat(who, toBe);
                                deads.add(toBe);
                            }
                        }
                );
            }
        }

        int count_dead = deads.size();
        //    подчищаем всех съеденных
        alives.removeAll(deads);
        /* todo::    можно сделать, чтобы трупы оставались, потом гнили, увеличивался счетчик болезней у трупа, и если кто-то съест падаль - то заболевает, получая счетчик ill
            или на трупе может вырасти трава или дерево :}
        */
        return count_dead;
    }


}
