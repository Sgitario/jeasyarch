package io.jeasyarch.api.extensions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.jeasyarch.api.Service;
import io.jeasyarch.core.DependencyContext;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ServiceContext;

public interface ExtensionBootstrap {

    boolean appliesFor(JEasyArchContext context);

    default void beforeAll(JEasyArchContext context) {

    }

    default void afterAll(JEasyArchContext context) {

    }

    default void beforeEach(JEasyArchContext context) {

    }

    default void afterEach(JEasyArchContext context) {

    }

    default void onSuccess(JEasyArchContext context) {

    }

    default void onDisabled(JEasyArchContext context, Optional<String> reason) {

    }

    default void onError(JEasyArchContext context, Throwable throwable) {

    }

    default void onServiceLaunch(JEasyArchContext context, Service service) {

    }

    default void updateContext(JEasyArchContext context) {

    }

    default void updateServiceContext(ServiceContext context) {

    }

    default List<Class<?>> supportedParameters() {
        return Collections.emptyList();
    }

    default Optional<Object> getParameter(DependencyContext dependency) {
        return Optional.empty();
    }
}
