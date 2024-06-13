package com.timas.projects.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Entity {
   /* Вес сущности */
   char icon;
   float weight;
   float maxWeight;

   /* Максимальное количество сущности этого вида на одной клетке */
   int maxAmount;
}
