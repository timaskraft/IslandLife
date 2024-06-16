package com.timas.projects.game.world;

import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cell {

    private Collection<Entity> value = new HashSet<>();

    public void clear()
    {
        value.clear();
    }


}
