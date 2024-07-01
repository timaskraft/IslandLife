package com.timas.projects.game.entity.alive.fauna.animals;


import com.timas.projects.annotations.Config;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true)
@Config(filename = "config/entity/alive/fauna/animals/wolf.yaml")
public class Wolf extends Animal{
}