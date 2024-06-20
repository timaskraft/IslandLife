package com.timas.projects.game.relation;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.timas.projects.annotations.Config;
import com.timas.projects.exeptions.InitException;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.entity.alive.Alive;
import com.timas.projects.game.world.World;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString(callSuper=true, includeFieldNames=true)
@Config(filename = "config/relation/relation_eaten.yaml")
public class RelationEaten {

    private  Map<Class<? extends Alive>, Map<Class <? extends Alive> , Integer>>  relation = new HashMap<>();


    //TODO:: Требуетяс рефактр этих методов в сервис

    // Метод для добавления отношения и его веса в карту
    public void addRelation(Class<? extends Entity> who, Class<? extends Entity> whom, Integer weight) {
        Map<Class<? extends Alive>, Integer> whomMap = relation.getOrDefault(who, new HashMap<>());
        whomMap.put((Class<? extends Alive>) whom, weight);
        relation.put((Class<? extends Alive>) who, whomMap);
    }

    public Map<Class <? extends Alive> , Integer> getEatenMap(Class<? extends Alive> who_key )
    {
        return relation.getOrDefault(who_key, new HashMap<>());//get(who_key);
    }
    public Integer getEaten(Class<? extends Alive> who_key, Class<? extends Alive> whom_key )
    {
        return getEatenMap(who_key).get(whom_key);
    }


}
