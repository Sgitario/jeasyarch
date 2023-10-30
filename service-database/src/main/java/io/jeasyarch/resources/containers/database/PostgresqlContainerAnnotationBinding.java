package io.jeasyarch.resources.containers.database;

import java.lang.annotation.Annotation;

import io.jeasyarch.api.DatabaseService;
import io.jeasyarch.api.PostgresqlContainer;
import io.jeasyarch.api.Service;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.containers.ContainerAnnotationBinding;

public class PostgresqlContainerAnnotationBinding extends ContainerAnnotationBinding {

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, PostgresqlContainer.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        PostgresqlContainer metadata = findAnnotation(annotations, PostgresqlContainer.class).get();

        if (!(service instanceof DatabaseService)) {
            throw new IllegalStateException("@PostgresqlContainer can only be used with DatabaseService service");
        }

        DatabaseService databaseService = (DatabaseService) service;
        databaseService.withJdbcName(metadata.jdbcName());
        databaseService.withDatabaseNameProperty(metadata.databaseNameProperty());
        databaseService.withUserProperty(metadata.userProperty());
        databaseService.withPasswordProperty(metadata.passwordProperty());

        return doInit(context, service, metadata.image(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }
}
