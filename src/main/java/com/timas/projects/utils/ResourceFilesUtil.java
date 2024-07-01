package com.timas.projects.utils;

import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class ResourceFilesUtil {
    public static Set<File> getFiles(String path, String ext) throws URISyntaxException, IOException {

        Path resourcesPath = Paths.get(path);

        try (Stream<Path> walk = Files.walk(resourcesPath)) {
            Set<File> filenames = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString()
                            .endsWith(ext))
                    .map(Path::toFile)
                    .collect(Collectors.toSet());
            return filenames;
        }

    }
}
