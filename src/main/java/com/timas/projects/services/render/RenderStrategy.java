package com.timas.projects.services.render;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.world.Field;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public  interface RenderStrategy {
    void render(RenderParam renderParam);

    default  Map<Class<? extends Entity>, List<Entity>> groupEntitiesByClass(Collection<Entity> entities) {
        return entities.stream()
                .collect(Collectors.groupingBy(Entity::getClass));
    }

    default Map<Class<? extends Entity>, List<Entity>> groupEntitiesByClassWithFilter(Collection<Entity> entities, Class<? extends Entity> superClass) {
        return entities.stream()
                .filter(entity -> superClass.isAssignableFrom(entity.getClass()))
                .collect(Collectors.groupingBy(Entity::getClass));
    }

}