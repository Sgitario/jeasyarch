package io.jeasyarch.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.condition.OS;

public final class ClassPathUtils {
    private static final Path MAVEN_SOURCE_CLASSES_LOCATION = Paths.get("target", "classes");
    private static final Path GRADLE_SOURCE_CLASSES_LOCATION = Paths.get("build", "classes");
    private static final String CLASS_SUFFIX = ".class";

    private ClassPathUtils() {

    }

    public static Class<?>[] findAllClassesFromSource(Path location) {
        List<Class<?>> classes = new LinkedList<>();
        classes.addAll(findAllClassesFromSource(location, MAVEN_SOURCE_CLASSES_LOCATION));
        classes.addAll(findAllClassesFromSource(location, GRADLE_SOURCE_CLASSES_LOCATION));
        return classes.toArray(new Class<?>[0]);
    }

    private static List<Class<?>> findAllClassesFromSource(Path location, Path classesLocation) {
        Path sourceClassLocation = location.resolve(classesLocation);
        List<Class<?>> classes = new LinkedList<>();
        try {
            if (!Files.exists(sourceClassLocation)) {
                return Collections.emptyList();
            }
            try (Stream<Path> stream = Files.walk(sourceClassLocation)) {
                stream.map(Path::toString).filter(s -> s.endsWith(CLASS_SUFFIX))
                        .map(s -> ClassPathUtils.normalizeClassName(sourceClassLocation, s)).forEach(className -> {
                            try {
                                classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                            } catch (ClassNotFoundException ignored) {
                                // classes are located in a different location
                            }
                        });
            }
        } catch (Exception ex) {
            throw new RuntimeException("Can't load source classes location.", ex);
        }

        return classes;
    }

    private static String normalizeClassName(Path sourceClassLocation, String path) {
        String source = sourceClassLocation.relativize(Paths.get(path)).toString().replace(CLASS_SUFFIX,
                StringUtils.EMPTY);
        if (OS.WINDOWS.isCurrentOs()) {
            source = source.replace("\\", ".");
        } else {
            source = source.replace("/", ".");
        }

        return source;
    }
}
