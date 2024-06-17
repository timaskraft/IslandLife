package com.timas.projects.services.render;

import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.world.Field;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RenderService {
    private RenderStrategy renderStrategy;

    public void setRenderStrategy(RenderStrategy renderStrategy) {
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