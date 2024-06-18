package com.timas.projects.services.render;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.game.world.Field;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j
public class ConsoleRenderStrategy implements RenderStrategy{

    final static String EMPTY = "\uD83D\uDFEB";
    final static char NOT_EMPTY = '▢';


    @Override
    public void render(RenderParam renderParam) {

        renderField(renderParam);

        renderStatistic(renderParam);
    }


    private void renderField(RenderParam renderParam)
    {
        System.out.println(myFormatStr('='," IslandLife statistics, cycle "+renderParam.tick+" ",renderParam.field.getSizeX() * 4));
        for (int y = 0; y < renderParam.field.getSizeY(); y++) {
            for (int x = 0; x < renderParam.field.getSizeX(); x++) {

                Collection<Entity> entityCollection = renderParam.field.getCollectionFromCell(x,y);

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
        System.out.println("print statistic:"+entities.size());
        entities.forEach((aClass, entity)->{

            String icon = entity.get(0).getIcon();
            int count = (int) entity.stream().count();

            String message = String.format("%s %-10s :%d",icon, aClass.getSimpleName(),count);
            System.out.println(message);
        });
    }


    Map<Class<? extends Entity>, List<Entity>> groupEntitiesByClassWithFilter(Collection<Entity> entities, Class<? extends Entity> superClass) {

        Map<Class<? extends Entity>, List<Entity>> map =
                entities.parallelStream()
                .filter(entity -> superClass.isAssignableFrom(entity.getClass()))
                .collect(Collectors.groupingBy(Entity::getClass));

        return  map;
    }


    private void renderStatistic(RenderParam renderParam)
    {


            Collection<Entity> allEntity = new ArrayList<>(renderParam.field.getSnapshotField());

            System.out.println("renderStatistic");

            Map<Class<? extends Entity>, List<Entity>> faunas = groupEntitiesByClassWithFilter(allEntity,Fauna.class);

            System.out.println(faunas);

            System.out.println(myFormatStr('=',"",renderParam.field.getSizeX() * 4));
            System.out.println("Fauna:");
            printStatisticOfClass(faunas);

            Map<Class<? extends Entity>, List<Entity>> floras = groupEntitiesByClassWithFilter(allEntity,Flora.class);

            System.out.println("Flora:");
            printStatisticOfClass(floras);
            System.out.println(myFormatStr('=',"",renderParam.field.getSizeX() * 4));


    }

    private static String myFormatStr(char symbol, String text, int length)
    {
        int paddingSize = (length - text.length()) / 2;
        String symbolStr = String.valueOf(symbol);
        String padding = new String(new char[paddingSize]).replace("\0", symbolStr);
        if ((text.length() + paddingSize * 2) < length) {
            padding += symbolStr;
        }
        return padding + text + padding;
    }
}
