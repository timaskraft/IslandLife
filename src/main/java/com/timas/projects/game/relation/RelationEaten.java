package com.timas.projects.game.relation;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.timas.projects.annotations.Config;
import com.timas.projects.exeptions.InitException;
import com.timas.projects.game.entity.Entity;
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

    private  Map<Class<? extends Entity>, Map<Class <? extends Entity> , Integer>>  relation = new HashMap<>();


    //TODO:: Требуетяс рефактр этих методов в сервис

    // Метод для добавления отношения и его веса в карту
    public void addRelation(Class<? extends Entity> who, Class<? extends Entity> whom, Integer weight) {
        Map<Class<? extends Entity>, Integer> whomMap = relation.getOrDefault(who, new HashMap<>());
        whomMap.put(whom, weight);
        relation.put(who, whomMap);
    }

    public Map<Class <? extends Entity> , Integer> getEatenMap(Class<? extends Entity> who_key )
    {
        return relation.get(who_key);
    }
    public Integer getEaten(Class<? extends Entity> who_key, Class<? extends Entity> whom_key )
    {
        return getEatenMap(who_key).get(whom_key);
    }


}
