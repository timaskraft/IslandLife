package com.timas.projects.game.world;

import com.timas.projects.annotations.Config;
import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.HashSet;
@Log4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Config(filename = "config/world/world.yaml")
public class World {

    int sizeX;
    int sizeY;

    @ToString.Exclude
    Field gameField;

    public World init()
    {
        return init(this.sizeX,this.sizeY);
    }

    public World init(int sizeX, int sizeY)
    {
        gameField = new Field(sizeX,sizeY);

        return this;
    }

/*
    public void printWorldToConsole()
    {
        for (int y = 0; y < gameField.length; y++) {
         for (int x = 0; x < gameField[y].length; x++) {
                 if (gameField[y][x].isEmpty())
                      System.out.print(".");
                 else System.out.print(" ");
            }
            System.out.println();
        }
    }
*/

    //public void seedEntities()
}

