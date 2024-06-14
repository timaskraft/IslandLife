package com.timas.projects.model.alive;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.timas.projects.model.Entity;
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

    /**
     * Сколько килограммов пищи нужно существу для полного насыщения
     */
    @JsonProperty
    float food;

    @JsonProperty
    float maxFood;
}
