package com.timas.projects.services.render;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@RequiredArgsConstructor
public class RenderService {
    private RenderStrategy renderStrategy;

    public RenderService(RenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }

    public void render(RenderParam renderParam) {
        if (renderStrategy != null) {
            renderStrategy.render(renderParam);
        } else {
            throw new IllegalStateException("Render strategy not set");
        }
    }


}