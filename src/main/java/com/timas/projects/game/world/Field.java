package com.timas.projects.game.world;

import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field {
    //Cell[][] field ;
    // ArrayList<ArrayList<Cell>> field;

    ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Cell>> field ;

    public Field(int sizeX, int sizeY)
    {
        if (sizeX<=0 | sizeY<=0) {
            String message = String.format("size X and size Y must be >0 ( x= %s y=%s )",sizeX,sizeY);
            throw new IllegalArgumentException(message);
        }

        field = new ConcurrentHashMap<>();

        for (int y = 0; y < sizeY; y++) {

            ConcurrentHashMap<Integer, Cell> newRow = new ConcurrentHashMap<>();
            for (int x = 0; x < sizeX; x++) {
                newRow.put(x, new Cell());
            }

            field.put(y,newRow);
        }
        log.debug("field create ");
    }

    public int getSizeY()
    {
        return field.size();
    }

    public int getSizeX()
    {
        return field.get(0).size();
    }

    public Cell getCell(int x, int y)
    {
        return field.get(y).get(x); // field[y][x];
    }

    public Collection<Entity> getAllCollection()
    {
        return field.values().parallelStream()
                .flatMap(m -> m.values().stream())
                .map(Cell::getValue)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    public Collection<Entity> getCollectionFromCell(int x, int y)
    {
        return  field.get(y).get(x).getValue(); // field[y][x].getValue();
    }
    public ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Cell>> getField()
    {
        return field;
    }


    Optional<Entity> getFirstEntity(int x,int y,Class<? extends Entity> aClass)
    {
       return getCollectionFromCell(x,y).parallelStream().filter(e -> aClass.isAssignableFrom(e.getClass())).findFirst();
    }


    public long getEntityCount(int x,int y,Class<? extends Entity> aClass)
    {
        return getCollectionFromCell(x,y)
                .parallelStream()
                .filter(e -> aClass.isAssignableFrom(e.getClass()))
                .count();
    }
    public Collection<Entity> getEntityFromCoordinate(int x,int y,Class<? extends Entity> aClass)
    {
        return getCollectionFromCell(x,y)
                .parallelStream()
                .filter(e -> aClass.isAssignableFrom(e.getClass())).collect(Collectors.toSet());

    }

    public void setCollectionToCell(int x,int y, Collection<Entity> collection)
    {
        field.get(y).get(x).setValue(collection);
    }

    public boolean isValidCoordinates(int x, int y)
    {
        return ((x>=0 & x< getSizeX()) & (y>=0 & y< getSizeY()) );
    }

    public boolean isEmptyField(int x, int y)
    {
        if (isValidCoordinates(x,y))
             return  field.get(y).get(x).getValue().isEmpty(); // field[x][y].getValue().isEmpty();
        else return false;
    }


}
