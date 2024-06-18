package com.timas.projects.game.entity.alive;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.timas.projects.game.entity.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс Живое
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper=true)
abstract public class Alive extends Entity {
    /**
     * Скорость перемещения, не более чем, клеток за ход
     */
    @JsonProperty
    int speed;

    @JsonProperty
    int maxSpeed;

    /* сытость */
    @JsonProperty
    float food;

    /**
     * Сколько килограммов пищи нужно существу для полного насыщения
     */
    @JsonProperty
    float maxFood;

    /* todo::степень заболевания, 0 - здоров. */
    @JsonProperty
    int illness = 0;


}