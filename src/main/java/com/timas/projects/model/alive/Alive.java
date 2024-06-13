package com.timas.projects.model.alive;


import com.timas.projects.model.Entity;

/**
 * Класс Живое
 */
abstract public class Alive extends Entity {
    /**
     * Скорость перемещения, не более чем, клеток за ход
     */
    int speed;
    int maxSpeed;

    /**
     * Сколько килограммов пищи нужно существу для полного насыщения
     */
    float food;
    float maxFood;
}
