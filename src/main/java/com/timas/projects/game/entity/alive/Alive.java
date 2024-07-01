package com.timas.projects.game.entity.alive;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.timas.projects.game.entity.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Класс Живое
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
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

    /* Полоска жизни, пока что 100, потом возможно каждой сущности назначить из конфига. Если = 0, то оно мертво */
    @JsonProperty
    int live = 100;

    /* Сколько тиков возраст этого организма */
    @JsonProperty
    int age = 0;


}