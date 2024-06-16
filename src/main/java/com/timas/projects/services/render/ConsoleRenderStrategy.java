package com.timas.projects.services.render;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.game.world.Field;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Log4j
public class ConsoleRenderStrategy implements RenderStrategy{

    final static String EMPTY = "\uD83D\uDFEB";
    final static char NOT_EMPTY = '▢';


    @Override
    public void render(Field field) {
        renderField(field);

        renderStatistic(field);
    }

    private void renderField(Field field)
    {
        System.out.println("IslandLife:");
        for (int y = 0; y < field.getSizeY(); y++) {
            for (int x = 0; x < field.getSizeX(); x++) {

                Collection<Entity> entityCollection = field.getCollectionFromCell(x,y);

                if(entityCollection.isEmpty()) {
                    System.out.print(EMPTY);
                }else
                {
                    Fauna fauna = entityCollection.stream()
                            .filter(entity -> entity instanceof Fauna)
                            .map(entity -> (Fauna) entity)
                            .findFirst()
                            .orElse(null);

                    if (fauna!=null) {

                        System.out.print(fauna.getIcon());
                    }
                    else {
                        Flora flora = entityCollection.stream()
                                .filter(entity -> entity instanceof Flora)
                                .map(entity -> (Flora) entity)
                                .findFirst()
                                .orElse(null);
                        if (flora!=null)
                            System.out.print(flora.getIcon());
                        else
                            System.out.print(NOT_EMPTY);

                    }

                }

                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void printStatisticOfClass(Map<Class<? extends Entity>, List<Entity>> entities)
    {
        entities.forEach((aClass, entity)->{

            String icon = entity.get(0).getIcon();
            int count = (int) entity.stream().count();

            String message = String.format("%s %-10s :%d",icon, aClass.getSimpleName(),count);
            System.out.println(message);
        });
    }
    private void renderStatistic(Field field)
    {
        System.out.println("============ Statistics ===========");
        Collection<Entity> allEntity = field.getAllEntity();

        Map<Class<? extends Entity>, List<Entity>> faunas = groupEntitiesByClassWithFilter(allEntity,Fauna.class);

        System.out.println("Fauna:");
        printStatisticOfClass(faunas);

        Map<Class<? extends Entity>, List<Entity>> floras = groupEntitiesByClassWithFilter(allEntity,Flora.class);

        System.out.println("Flora:");
        printStatisticOfClass(floras);
        System.out.println("===================================");


    }
}
