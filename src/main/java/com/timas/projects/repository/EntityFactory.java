package com.timas.projects.repository;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.timas.projects.annotations.Config;
import com.timas.projects.exeptions.InitException;
import com.timas.projects.game.entity.Entity;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.AccessLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Log4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityFactory {

    final static String ENTITIES_PATH = "com.timas.projects.game.entity";
    final static String DEFAULT_CONFIG_ENTITIES_DIR = "src/main/resources/config/entity";

    /* Все возможные классы Entity в программе */
    private static Set<Class<? extends Entity>> TYPES_OF_ENTITY;
    private static Map<Class<? extends Entity>, Entity> PROTOTYPE_OF_ENTITY;

    private static Map<Class<? extends Entity>, Entity> entitiesDefault;

    static {
        /* init Type OF entities */

        TYPES_OF_ENTITY = new HashSet<>();

        PROTOTYPE_OF_ENTITY = new HashMap<>();

        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages(ENTITIES_PATH)
                .scan()) {
            for (ClassInfo classInfo : scanResult
                    .getSubclasses(Entity.class)
                    .filter(classInfo -> !classInfo.isAbstract())
            ) {

                TYPES_OF_ENTITY.add((Class<? extends Entity>) classInfo.loadClass());
            }
        }

        init();
    }

    Map<Class<? extends Entity>, AtomicLong> number = new HashMap<>();

    /* init default set Entity */
    private static void init() {
        //Get files entity

        for (Class<? extends Entity> type : TYPES_OF_ENTITY) {
            log.debug(type);

            Entity entity = createEntityPrototype(type);
            PROTOTYPE_OF_ENTITY.put(type, entity);
        }


    }

    private static Entity createEntityPrototype(Class<? extends Entity> type) {
        if (!type.isAnnotationPresent(Config.class)) {
            throw new IllegalArgumentException(
                    String.format("Prototype Class %s must have @Config annotation", type.getSimpleName())
            );
        }
        URL resource = getConfigFilePath(type);
        return loadEntity(resource, type);

    }

    private static URL getConfigFilePath(Class<? extends Entity> type) {
        Config config = type.getAnnotation(Config.class);
        return type.getClassLoader().getResource(config.filename());
    }

    @SneakyThrows
    private static Entity loadEntity(URL resource, Class<? extends Entity> type) {
        YAMLMapper yamlMapper = new YAMLMapper();
        Entity entity;
        try {
            entity = yamlMapper.readValue(resource, type);

        } catch (Exception e) {
            String message = String.format("Cannot find config file %s, for class %s", resource.getFile(), type);
            throw new InitException(message, e);
        }
        return entity;
    }

    public Entity getPrototypeEntity(Class<? extends Entity> type) {
        if (PROTOTYPE_OF_ENTITY.containsKey(type)) {
            return PROTOTYPE_OF_ENTITY.get(type);
        }

        Entity entity = createEntityPrototype(type);
        PROTOTYPE_OF_ENTITY.put(type, entity);

        return entity;

    }

    public Collection<Entity> getPrototypesEntities(Class<?> entityClass) {
        return PROTOTYPE_OF_ENTITY.values().parallelStream()
                .filter(entityClass::isInstance)
                .collect(Collectors.toList());
    }

    public Set<Class<? extends Entity>> getTypesOfEntities() {
        return TYPES_OF_ENTITY;
    }


    public Entity cloneEntity(Entity template) throws CloneNotSupportedException {

        Entity cloned = (Entity) template.clone();

        number.computeIfAbsent(cloned.getClass(), k -> new AtomicLong(0));

        long uniqueNumber = number.get(cloned.getClass()).incrementAndGet();
        cloned.setName(cloned.getClass().getSimpleName() + "_" + uniqueNumber);

        return cloned;
    }


}
