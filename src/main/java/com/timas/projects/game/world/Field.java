package com.timas.projects.game.world;

import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field {
    Cell[][] field ;

    public Field(int sizeX, int sizeY)
    {
        if (sizeX<=0 | sizeY<=0) {
            String message = String.format("size X and size Y must be >0 ( x= %s y=%s )",sizeX,sizeY);
            throw new IllegalArgumentException(message);
        }

        field = new Cell[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                field[y][x] = new Cell();
            }
        }
    }

    public int getSizeY()
    {
        return field.length;
    }

    public int getSizeX()
    {
        return field[0].length;
    }
    public Collection<Entity> getCollectionFromCell(int x, int y)
    {
        return field[y][x].getValue();
    }
    public Collection<Entity> getAllEntity()
    {
        return Stream.of(field)
                .flatMap(Stream::of)
                .map(Cell::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }


    public void setCollectionToCell(int x,int y, Collection<Entity> collection)
    {
        field[y][x].setValue(collection);
    }

    public boolean isValidCoordinates(int x, int y)
    {
        return ((x>=0 & x< getSizeX()) & (y>=0 & y< getSizeY()) );
    }

    public boolean isEmptyField(int x, int y)
    {
        if (isValidCoordinates(x,y))
             return field[x][y].getValue().isEmpty();
        else return false;
    }


}
