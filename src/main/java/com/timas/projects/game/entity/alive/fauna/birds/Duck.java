package com.timas.projects.game.entity.alive.fauna.birds;

import com.timas.projects.annotations.Config;
import com.timas.projects.game.entity.alive.fauna.animals.Animal;
import lombok.ToString;

import java.io.Serializable;

@ToString(callSuper=true, includeFieldNames=true)
@Config(filename = "config/entity/alive/fauna/birds/duck.yaml")
public class Duck extends Animal {
}