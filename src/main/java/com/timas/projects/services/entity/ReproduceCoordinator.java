package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.game.world.Cell;
import com.timas.projects.services.random.RandomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Координатор репродукция сущностей. Кого, и как репродуцировать в переданном списке.
 */
@Log4j
@RequiredArgsConstructor
public class ReproduceCoordinator {

    @Getter
    private final RandomService randomService;

    @Getter
    private final ReproduceService reproduceService;

    /* Генерация списка из списка, не более чем MaxAmount */
    /* Если без параметра, то взять всех возможных с фабрики */
    /* Если указан факто генерации, то максимальное количество возможных
       в одной клетке будет maxAmount / factor_generate
     */

    public Collection<Entity> generate()
    {
        return  generate(1);
    }
    public Collection<Entity> generate(int factor_generate)
    {
        return generate(reproduceService.getEntityFactory().getPrototypesEntities(Alive.class),factor_generate );
    }

    public Collection<Entity> generate(Class <? extends Entity> aClass)
    {
        return generate(reproduceService.getEntityFactory().getPrototypesEntities(aClass),1);
    }

    public Collection<Entity> generate(Collection<Entity> entityCollection,int factor_generate)
    {
        List<Entity> newList = new ArrayList<>();
        int finalFactor_generate = Math.max(factor_generate, 1);
        entityCollection.forEach(entity -> {
            // случайно возможное количество вида на клетке, шанс не боле чем.
            int possible_count_in_cell = randomService.nextInt(0,entity.getMaxAmount()/ finalFactor_generate); //   randomService.chanceReceived(entity.getChanceReproduce());
            for (int i = 0; i < possible_count_in_cell; i++) {
                Entity bornEntity = born(entity);
                if (bornEntity!=null)
                    newList.add(bornEntity);
            }
        });

        return newList;
    }

    /* Родить сущность от parent , если это фауна - то назначить пол */
    /* Пол нужен будет чтобы процедура рождения была гендерно зависимой :)


    /* Родить от святого духа */
    public Entity born(Entity mother)
    {
        return born(mother,null);
    }
    /* Родить от матери и отца */
    public Entity born(Entity mother,Entity father)
    {
        if (mother==null)
            throw new IllegalArgumentException("wrong argument for born function");

        try {
            Entity bornEntity = null;
            if (father == null)
            {
                if ( randomService.takeChance(mother.getChanceReproduce())  )
                                     bornEntity = reproduceService.reproduce(mother);
            }else
            {
                if (       randomService.takeChance(mother.getChanceReproduce())
                        &  randomService.takeChance(father.getChanceReproduce())
                   )
                                     bornEntity = reproduceService.reproduce(mother);
            }

            if (bornEntity instanceof Fauna) {
                ((Fauna) bornEntity).setMale(randomService.nextBoolean());
            }
            return bornEntity;
        }catch (CloneNotSupportedException e)
        {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }
}
