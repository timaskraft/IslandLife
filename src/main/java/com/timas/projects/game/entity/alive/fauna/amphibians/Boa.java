package com.timas.projects.game.entity.alive.fauna.amphibians;

import com.timas.projects.annotations.Config;
import com.timas.projects.game.entity.alive.fauna.animals.Animal;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true)
@Config(filename = "config/entity/alive/fauna/amphibias/boa.yaml")
public class Boa extends Amphibia {
}