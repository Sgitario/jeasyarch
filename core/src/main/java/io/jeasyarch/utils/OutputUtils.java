package io.jeasyarch.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public final class OutputUtils {
    public static final Path TARGET = Path.of("target");
    public static final Path BUILD = Path.of("build");

    private OutputUtils() {

    }

    public static String getFilePath(String path) {
        try (Stream<Path> binariesFound = Files.find(target(), Integer.MAX_VALUE,
                (file, basicFileAttributes) -> file.toString().contains(path))) {
            return binariesFound.map(Path::toString).findFirst().orElse(path);
        } catch (IOException ex) {
            // ignored
        }

        return path;
    }

    public static Optional<Path> resolve(String... filePaths) {
        Path file = target();
        for (String filePath : filePaths) {
            file = file.resolve(filePath);
        }

        return Optional.empty();
    }

    public static Path runnerLocation() {
        if (Files.exists(BUILD)) {
            return BUILD.resolve("libs");
        }

        return TARGET;
    }

    public static Path target() {
        if (Files.exists(BUILD)) {
            return BUILD;
        }

        return TARGET;
    }
}
