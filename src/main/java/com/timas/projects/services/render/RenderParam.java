package com.timas.projects.services.render;

import com.timas.projects.game.world.Field;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
public class RenderParam {
    long tick;
    Field field;
}
