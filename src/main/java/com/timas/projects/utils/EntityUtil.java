package com.timas.projects.utils;

import com.timas.projects.game.entity.Entity;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
public class EntityUtil {

    /**
     * @param entities
     * @param superClass
     * @return map group superclass, class
     */
    public static Map<Class<? extends Entity>, Long> groupAndCountEntities(
            Class<? extends Entity> superClass,
            Collection<Entity> entities) {
        return entities.parallelStream()
                .filter(superClass::isInstance)
                .collect(Collectors.groupingBy(
                        Entity::getClass,
                        Collectors.counting()
                ));
    }

    public static Map<Class<? extends Entity>, List<Entity>> groupSuperClassEntities(
            Class<? extends Entity> superClass,
            Collection<Entity> entities) {
        return entities.parallelStream()
                .filter(superClass::isInstance)
                .collect(Collectors.groupingBy(
                        Entity::getClass));
    }

    public static Set<Entity> getSetEntitiesOfClass(
            Class<? extends Entity> superClass,
            Collection<Entity> entities) {
        return entities.parallelStream()
                .filter(superClass::isInstance)
                .collect(Collectors.toSet());
    }


}
