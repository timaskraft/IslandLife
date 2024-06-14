package com.timas.projects.model.alive.fauna.animal;

import com.timas.projects.model.alive.fauna.Fauna;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Animal extends Fauna {
}
