package com.timas.projects.lifecycle.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class RoutineTask implements Runnable{

    private long tick =0;

    @Override
    public void run() {
        tick ++;

        log.debug("routine "+tick);
    }
}
