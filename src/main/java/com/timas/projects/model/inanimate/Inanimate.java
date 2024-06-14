package com.timas.projects.model.inanimate;


import com.timas.projects.model.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Класс Неживое
 */
@Getter
@Setter
@ToString(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Inanimate extends Entity {
}
