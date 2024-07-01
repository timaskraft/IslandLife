package com.timas.projects.game.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Entity implements Cloneable{

   String name;

   @JsonProperty
   String icon;

   @JsonProperty
   float weight;

   @JsonProperty
   float maxWeight;

   @JsonProperty
   int maxAmount;

   @JsonProperty
   int chanceSpawn = 50; //default chance reproduce or spawn 50x50

   @Override
   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

}