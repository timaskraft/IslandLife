package com.timas.projects.game.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
/* вспомогательный класс координат */
@Getter
@Setter
public class Coordinate {
    private int x;
    private int y;

    public Coordinate()
    {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{"+x+":"+y+"}";
    }
}