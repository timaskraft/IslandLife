package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.repository.EntityFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Getter
@Log4j
@RequiredArgsConstructor
public class ReproduceService {

    private final EntityFactory entityFactory;

    public Entity reproduce(Entity entity) throws CloneNotSupportedException {
        Entity cloneEntity = entityFactory.cloneEntity(entity);

        return cloneEntity;
    }

}
