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

    public void render(Field field) {
        if (renderStrategy != null) {
            renderStrategy.render(field);
        } else {
            throw new IllegalStateException("Render strategy not set");
        }
    }


}