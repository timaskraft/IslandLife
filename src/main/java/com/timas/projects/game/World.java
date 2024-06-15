package com.timas.projects.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class World implements Serializable {
    int sizeX;
    int sizeY;
}
