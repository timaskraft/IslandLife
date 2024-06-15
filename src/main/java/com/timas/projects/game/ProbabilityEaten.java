package com.timas.projects.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProbabilityEaten {

    Map<Class<?>, Map<Class<?>, Integer>> map;

    public ProbabilityEaten(Map<String,Map<String,Integer>> property,Map<String, Class<?>> whiteList)
    {
        Map<String,Map<String,Integer>> filtered_property = getFiltered_probabilityEatenProperties(property,whiteList);
        log.debug("ProbabilityEaten :"+filtered_property);
        this.map = convertProbabilityEatenPropertiesToMap( filtered_property, whiteList );

    }

    private Map<Class<?>, Map<Class<?>, Integer>> convertProbabilityEatenPropertiesToMap(Map<String,Map<String,Integer>> property, Map<String, Class<?>> whiteList)
    {
        Map<Class<?>, Map<Class<?>, Integer>>  map = new HashMap<>();
        property.forEach((whoKey, whomMap) -> {
                Class<?> whoClass = whiteList.get(whoKey);
                Map<Class<?>, Integer> classMap = whomMap.entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                whomEntry -> {
                                        return whiteList.get(whomEntry.getKey());
                                },
                                Map.Entry::getValue
                        ));
                map.put(whoClass, classMap);
        });
        return map;
    }


/**
 * Фильтрует в соответсвии с белым списком
 * @param property
 * @param whiteList
 * @return
 */
    private Map<String,Map<String,Integer>> getFiltered_probabilityEatenProperties(Map<String,Map<String,Integer>> property,Map<String, Class<?>> whiteList)
    {
        Map<String,Map<String,Integer>> map = property
                .entrySet()
                .stream()
                //filtered WHO
                .filter(who->whiteList.containsKey(who.getKey()))
                //filtered WHOM
                .map(entry -> {
                    Map<String, Integer> filteredValue = entry.getValue().entrySet()
                            .stream()
                            .filter(whom -> whiteList.containsKey(whom.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    return new AbstractMap.SimpleEntry<>(entry.getKey(), filteredValue);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return map;
    }
}
