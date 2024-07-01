package com.timas.projects.game.entity.alive.fauna.birds;

import com.timas.projects.annotations.Config;
import lombok.ToString;

@ToString(callSuper = true, includeFieldNames = true)
@Config(filename = "config/entity/alive/fauna/birds/duck.yaml")
public class Duck extends Bird {
}