package com.timas.projects.services.entity;

import com.timas.projects.game.Direction;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.world.Coordinate;
import com.timas.projects.repository.WorldModifier;
import com.timas.projects.services.random.RandomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
@RequiredArgsConstructor
public class MoveCoordinator{
    final RandomService randomService;
    final MoveService moveService;

    private int direction_count = Direction.values().length-1;

    public Set<Alive> moving(WorldModifier worldModifier,Coordinate coordinate,Collection<Entity> alives)
    {
        Set<Alive> movd = ConcurrentHashMap.newKeySet();
        if (!alives.isEmpty())
        {

            /* только живые, только те, кто не ходил*/
            Iterator<Fauna> it = alives
                    .parallelStream()
                    .filter(Fauna.class::isInstance)
                    .map(Fauna.class::cast)
                    .filter(e->(e.getLive()>0 & e.getSpeed()==0))
                    .iterator();

            while (it.hasNext()) {
                    Alive who = it.next();

                    int randomStep = randomService.nextInt(0, who.getMaxSpeed());

                    List<Direction> directionList = new ArrayList<>(randomStep);

                    for (int i = 0; i < randomStep; i++) {
                        directionList.add( Direction.values()[ randomService.nextInt(0,direction_count) ] );
                    }

                    Coordinate step_coordinate = new Coordinate(coordinate.getX(),coordinate.getY());

                    for(Direction direction:directionList)
                    {

                        if (direction.getDeltaX()==0 & direction.getDeltaY()==0) continue;

                        if (!worldModifier.getWorld()
                                    .getGameField()
                                    .isValidCoordinates(
                                            step_coordinate.getX() + direction.getDeltaX(),
                                            step_coordinate.getY() + direction.getDeltaY()
                                    )
                               ) continue;

                        //check count entity class of WHO надо чтобы там было свободное место
                        Collection<Entity> overCrowded = worldModifier.getWorld()
                                                        .getGameField()
                                                        .getEntityFromCoordinate(
                                                                step_coordinate.getX() + direction.getDeltaX(),
                                                                step_coordinate.getY() + direction.getDeltaY(),
                                                                  who.getClass());

                        if (overCrowded.size() >= who.getMaxAmount()) continue;

                        step_coordinate.setX(step_coordinate.getX() + direction.getDeltaX());
                        step_coordinate.setY(step_coordinate.getY() + direction.getDeltaY());
                        try {

                            Collection<Entity> destination = worldModifier.getWorld()
                                                                .getGameField()
                                                                .getCollectionFromCell(
                                                                    step_coordinate.getX(),
                                                                    step_coordinate.getY());

                            if (moveService.move(who,alives, destination ) ) {
                                movd.add(who);
                                who.setSpeed(who.getSpeed() + 1);
                            }



                        } catch (CloneNotSupportedException e) {
                            log.error("Error move exception "+who.getName()+" to "+step_coordinate.getX()+":"+step_coordinate.getY());
                            throw new RuntimeException(e);
                        }

                    }
                }
        }

        return movd;

    }

}
