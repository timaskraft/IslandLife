package com.timas.projects.repository;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.world.Cell;
import com.timas.projects.game.world.Coordinate;
import com.timas.projects.game.world.Field;
import com.timas.projects.game.world.World;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Log4j
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
        for (int yy = 0; yy < world.getGameField().getSizeY(); yy++) {
            for (int xx = 0; xx < world.getGameField().getSizeX(); xx++) {

                Coordinate coordinate = new Coordinate(xx, yy);
                Cell cell = world.getGameField().getCell(xx,yy);
                operation.accept(coordinate,cell);

            }
        }
    }
}
