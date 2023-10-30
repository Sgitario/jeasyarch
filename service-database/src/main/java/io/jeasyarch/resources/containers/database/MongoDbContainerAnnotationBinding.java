package io.jeasyarch.resources.containers.database;

import java.lang.annotation.Annotation;

import io.jeasyarch.api.DatabaseService;
import io.jeasyarch.api.MongoDbContainer;
import io.jeasyarch.api.Service;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.containers.ContainerAnnotationBinding;

public class MongoDbContainerAnnotationBinding extends ContainerAnnotationBinding {

    private static final String URL_PATTERN = "${JDBC_NAME}://${HOST}:${PORT}";

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, MongoDbContainer.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        MongoDbContainer metadata = findAnnotation(annotations, MongoDbContainer.class).get();

        if (!(service instanceof DatabaseService)) {
            throw new IllegalStateException("@MongoDbContainer can only be used with DatabaseService service");
        }

        DatabaseService databaseService = (DatabaseService) service;
        databaseService.withJdbcName(metadata.jdbcName());
        databaseService.withJdbcUrlPattern(URL_PATTERN);
        databaseService.withReactiveUrlPattern(URL_PATTERN);

        return doInit(context, service, metadata.image(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }
}
