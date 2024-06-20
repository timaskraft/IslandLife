package com.timas.projects.lifecycle.tasks;

import com.timas.projects.game.world.World;
import com.timas.projects.services.render.RenderParam;
import com.timas.projects.services.render.RenderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class RenderTask extends LifeTask{
    final RenderService renderService;
    final World world;

    private RenderParam param = new RenderParam();

    @Override
    public void run() {

        count_tick ++;

        param.setTick(count_tick);
        param.setField(world.getGameField());
        renderService.render(param);

    }
}
