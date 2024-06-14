package com.timas.projects.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
abstract public class Entity {

   @JsonProperty
   char icon;

   @JsonProperty
   float weight;

   @JsonProperty
   float maxWeight;

   @JsonProperty
   int maxAmount;
}
