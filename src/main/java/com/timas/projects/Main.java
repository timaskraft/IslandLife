package com.timas.projects;

import com.timas.projects.utils.ResourceFilesUtil;
import lombok.extern.log4j.Log4j;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Концепция.
 * Entity - базовый абстрактный класс, который представляет собой сущность, от которой наследуються другие сущности,
 * которые располагаются на игровом поле.
 * Базовая сущность -> Живое   -> Фауна -> Животные -> Волк, Кролик
 *                                      -> Рыбы (для примера)
 *                                      -> Насекомые (для примера)
 *                                Флора -> Травы  -> Трава
 *                                                -> Цветы (для примера)
 *                                      -> Деревья (для примера)
 *                                      -> Водоросли (для примера)
 *
 *                  -> Неживое -> Камень (для примера)
 *                             -> Вода (для примера)
 *                             -> Лава (для примера)
 */

@Log4j
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        System.out.println("IslandLife->");


        log.debug("IslandLife starting...");
        log.info("IslandLife starting...");
        log.warn("IslandLife starting...");
        log.error("IslandLife starting...");

        log.debug( ResourceFilesUtil.getResourceFiles("src/main/resources") );



    }
}