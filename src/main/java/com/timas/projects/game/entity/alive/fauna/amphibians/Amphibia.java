package com.timas.projects.game.entity.alive.fauna.amphibians;

import com.timas.projects.game.entity.alive.fauna.Fauna;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Amphibia extends Fauna {
}