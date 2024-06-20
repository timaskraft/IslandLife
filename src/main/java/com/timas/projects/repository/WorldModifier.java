package com.timas.projects.repository;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.world.Cell;
import com.timas.projects.game.world.Coordinate;
import com.timas.projects.game.world.Field;
import com.timas.projects.game.world.World;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Log4j
@Getter
@RequiredArgsConstructor
public class WorldModifier {


    final World world;


    public void init()
    {
        init(world.getSizeX(), world.getSizeY());
    }

    public void init(int sizeX, int sizeY)
    {
        world.setGameField( new Field(sizeX,sizeY));
    }

    public void update( BiConsumer<Coordinate,Cell> operation)
    {
        Objects.requireNonNull(operation);


        var rowIterator = world.getGameField().getField().entrySet().iterator();
        while (rowIterator.hasNext()) {
            var yRowEntry = rowIterator.next();
            Integer y = yRowEntry.getKey();
            var colIterator = yRowEntry.getValue().entrySet().iterator();

            while (colIterator.hasNext()) {
                var xColEntry = colIterator.next();
                Integer x = xColEntry.getKey();
                Cell col = xColEntry.getValue();

                operation.accept(new Coordinate(x, y), col);
            }
        }

    }
}
