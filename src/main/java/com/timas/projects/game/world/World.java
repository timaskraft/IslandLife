package com.timas.projects.game.world;

import com.timas.projects.annotations.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Config(filename = "config/world/world.yaml")
public class World {

    int sizeX;
    int sizeY;

    @ToString.Exclude
    Field gameField;

}

