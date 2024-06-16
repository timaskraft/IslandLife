package com.timas.projects.game.entity.alive.flora.herbs;



import com.timas.projects.annotations.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;



@ToString(callSuper=true)
@Config(filename = "config/entity/alive/flora/herbs/grass.yaml")
public class Grass extends Herbs  {
}