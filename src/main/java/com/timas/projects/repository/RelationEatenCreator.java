package com.timas.projects.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.timas.projects.annotations.Config;
import com.timas.projects.exeptions.InitException;
import com.timas.projects.game.entity.Entity;
import com.timas.projects.game.relation.RelationEaten;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Set;

@Log4j
public class RelationEatenCreator {

    static Set<Class<? extends Entity>> entities;

    public RelationEatenCreator(Set<Class<? extends Entity>> entities) {
        this.entities = entities;
    }

    private static boolean isItOnEntities(String name_entity) {
        return entities.parallelStream()
                .anyMatch(entity -> entity.getSimpleName().equals(name_entity));
    }

    public RelationEaten getRelationEaten() {

        Config config = RelationEaten.class.getAnnotation(Config.class);

        URL url = RelationEaten.class.getClassLoader().getResource(config.filename());

        return loadRelationEaten(url);

    }

    private Class<? extends Entity> getClassFromKey(String key, Set<Class<? extends Entity>> entities) {
        return entities.parallelStream()
                .filter(entity -> entity.getSimpleName().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found for key: " + key));
    }

    @SneakyThrows
    private RelationEaten loadRelationEaten(URL resource) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RelationEaten relationEaten = new RelationEaten();
        try {
            Map<String, Map<String, Integer>> dataYaml = objectMapper.readValue(new File(resource.toURI()), Map.class);

            dataYaml.forEach((whoKey, whomValue) -> {
                //проверить, есть ли whokey в entities и загрузить только валидные

                if (isItOnEntities(whoKey)) {
                    log.debug(whoKey);
                    // Приведение типов для работы с вложенной картой
                    Map<String, Integer> whomMap = (Map<String, Integer>) whomValue;
                    whomMap.forEach((whomKey, weight) -> {
                        if (isItOnEntities(whomKey)) {
                            relationEaten.addRelation(getClassFromKey(whoKey, entities), getClassFromKey(whomKey, entities), weight);
                        }
                    });
                }
            });

            return relationEaten;

        } catch (Exception e) {
            String message = String.format("Cannot find config file %s, for class %s", resource.getFile(), RelationEaten.class);
            throw new InitException(message, e);
        }

    }


}
