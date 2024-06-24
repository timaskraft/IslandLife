package com.timas.projects.services.entity;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Getter
@Log4j
@RequiredArgsConstructor
public class FoodService {
    public <T extends Alive> Entity eat(T who,T whom){
        //todo:: who.setFood();
        whom.setLive(0);
        return whom;
    }
}
