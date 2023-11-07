package io.jeasyarch.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import io.jeasyarch.logging.Log;

public final class ServiceLoaderUtils {
    private ServiceLoaderUtils() {

    }

    public static <T> List<T> load(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        tryLoad(list, ServiceLoader.load(clazz).iterator());
        return list;
    }

    private static <T> void tryLoad(List<T> list, Iterator<T> iterator) {
        try {
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        } catch (ServiceConfigurationError error) {
            // this may fail because we're declaring optional dependencies.
            Log.trace("Error loading class", error);
            tryLoad(list, iterator);
        }
    }
}
