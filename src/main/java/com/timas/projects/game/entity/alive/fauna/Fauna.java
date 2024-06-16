package com.timas.projects.game.entity.alive.fauna;

import com.timas.projects.game.entity.alive.Alive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Фауна
 */
@Getter
@Setter
@ToString(callSuper=true)
abstract public class Fauna extends Alive {
    /**
     *  true - самец, false - самка
     **/
    boolean isMale;
}