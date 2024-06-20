package com.timas.projects.game.world;

import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cell {

    private Collection<Entity> value = ConcurrentHashMap.newKeySet();

    public void clear()
    {
        value.clear();
    }

    public void setValue(Collection<Entity> value) {
        this.value = value;
    }



}
