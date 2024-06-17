package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.relation.RelationEaten;
import com.timas.projects.services.random.RandomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;

@Log4j
@RequiredArgsConstructor
public class FoodCoordinator {
    private final RandomService randomService;
    private final FoodService reproduceService;
    private final RelationEaten relationEaten;

    public Collection<Entity> meal(Collection<Entity> entities)
    {
     //   Collection<Entity> mealEntities = n
        return null;
    }



}
