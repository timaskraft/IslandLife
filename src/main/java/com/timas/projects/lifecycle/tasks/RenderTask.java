package com.timas.projects.lifecycle.tasks;

import com.timas.projects.game.world.World;
import com.timas.projects.services.render.RenderParam;
import com.timas.projects.services.render.RenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class RenderTask implements Runnable{
    final RenderService renderService;
    final World world;

    private long render_count;
    private RenderParam param = new RenderParam();



    @Override
    public void run() {

        render_count ++;
        log.debug("render "+render_count);

        param.setTick(render_count);
        param.setField(world.getGameField());
        renderService.render(param);

    }
}
