package com.timas.projects.utils;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class ResourceFilesUtil {
    public static Set<String> getResourceFiles(String path) throws URISyntaxException, IOException {
        Set<String> filenames = new HashSet<>();

        Path resourcesPath = Paths.get(path);

        try (Stream<Path> walk = Files.walk(resourcesPath)) {
             List<Path> list = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".yaml"))
                    .toList();

             list.forEach(log::debug);


        }
        return filenames;
/*
        try {
        // Получаем URL ресурса по указанному пути
        URL dirURL = ResourceFilesUtil.class.getResource(path);
        log.debug(dirURL);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            try (Stream<Path> stream = Files.list(Paths.get(dirURL.toURI()))) {
                filenames = stream
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toSet());
            }
        }


 */

    }
}
