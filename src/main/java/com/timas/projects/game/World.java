package com.timas.projects.game;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;

@Log4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class World {

    final int sizeX;
    final int sizeY;

    void init()
    {

    }

}
