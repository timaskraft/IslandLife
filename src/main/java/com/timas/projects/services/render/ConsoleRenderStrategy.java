package com.timas.projects.services.render;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.fauna.Fauna;
import com.timas.projects.game.entity.alive.flora.Flora;
import com.timas.projects.game.entity.inanimate.Inanimate;
import com.timas.projects.utils.EntityUtil;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j
public class ConsoleRenderStrategy implements RenderStrategy {

    final static String EMPTY = "\uD83D\uDFEB";

    private static String myFormatStr(char symbol, String text, int length) {
        int paddingSize = (length - text.length()) / 2;
        String symbolStr = String.valueOf(symbol);
        String padding = new String(new char[paddingSize]).replace("\0", symbolStr);
        if ((text.length() + paddingSize * 2) < length) {
            padding += symbolStr;
        }
        return padding + text + padding;
    }

    @Override
    public void render(RenderParam renderParam) {

        renderField(renderParam);
        renderStatistic(renderParam);
    }

    private void renderField(RenderParam renderParam) {
        System.out.println(myFormatStr('=', " IslandLife statistics, cycle " + renderParam.tick + " ", renderParam.field.getSizeX() * 4));

        renderParam.field.getField().forEach((y, row) -> {
            row.forEach((x, col) -> {
                if (col.getValue().isEmpty()) {
                    System.out.print(EMPTY + " ");
                    return;
                }

                /* приоритет отображения на клетке - живое и животное.*/
                Optional<Fauna> first = col.getValue().stream()
                        .filter(Fauna.class::isInstance)
                        .map(Fauna.class::cast)
                        .filter(e -> e.getLive() > 0)
                        .findFirst();
                if (first.isPresent()) {
                    System.out.print(first.get().getIcon() + " ");
                    return;
                }

                Optional<Flora> second = col.getValue().stream()
                        .filter(Flora.class::isInstance)
                        .map(Flora.class::cast)
                        .filter(e -> e.getLive() > 0)
                        .findFirst();
                if (second.isPresent()) {
                    System.out.print(second.get().getIcon() + " ");
                    return;
                }
                /* неживое */
                Optional<Inanimate> third = col.getValue().stream()
                        .filter(Inanimate.class::isInstance)
                        .map(Inanimate.class::cast)
                        .findFirst();
                if (third.isPresent()) {
                    System.out.print(third.get().getIcon() + " ");
                    return;
                }

                System.out.print(EMPTY + " ");
            });
            System.out.println();
        });

    }

    private void printStatisticOfClass(Map<Class<? extends Entity>, List<Entity>> entities) {
        entities.forEach((aClass, entity) -> {

            String icon = entity.get(0).getIcon();
            int count = (int) entity.stream().count();

            String message = String.format("%s %-10s :%d", icon, aClass.getSimpleName(), count);
            System.out.println(message);
        });
    }

    private void renderStatistic(RenderParam renderParam) {

        Collection<Entity> allEntity = renderParam.field.getAllCollection();

        System.out.println(myFormatStr('=', "", renderParam.field.getSizeX() * 4));
        System.out.println("Fauna:");
        printStatisticOfClass(EntityUtil.groupSuperClassEntities(Fauna.class, allEntity));

        System.out.println("Flora:");
        printStatisticOfClass(EntityUtil.groupSuperClassEntities(Flora.class, allEntity));
        System.out.println(myFormatStr('=', "", renderParam.field.getSizeX() * 4));


    }
}
