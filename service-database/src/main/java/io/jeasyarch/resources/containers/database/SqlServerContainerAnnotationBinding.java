package io.jeasyarch.resources.containers.database;

import java.lang.annotation.Annotation;

import io.jeasyarch.api.DatabaseService;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.SqlServerContainer;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.containers.ContainerAnnotationBinding;

public class SqlServerContainerAnnotationBinding extends ContainerAnnotationBinding {

    private static final String USER = "sa";
    private static final String PASSWORD = "My1337p@ssworD";
    private static final String DATABASE = "msdb";
    private static final String JDBC_URL_PATTERN = "jdbc:${JDBC_NAME}://${HOST}:${PORT};databaseName=${DATABASE}";
    private static final String REACTIVE_URL_PATTERN = "${JDBC_NAME}://${HOST}:${PORT};databaseName=${DATABASE}";

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, SqlServerContainer.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        SqlServerContainer metadata = findAnnotation(annotations, SqlServerContainer.class).get();

        if (!(service instanceof DatabaseService)) {
            throw new IllegalStateException("@SqlServerContainer can only be used with DatabaseService service");
        }

        DatabaseService databaseService = (DatabaseService) service;
        databaseService.withJdbcName(metadata.jdbcName());
        databaseService.withPasswordProperty(metadata.passwordProperty());
        databaseService.withJdbcUrlPattern(JDBC_URL_PATTERN);
        databaseService.withReactiveUrlPattern(REACTIVE_URL_PATTERN);
        databaseService.with(USER, PASSWORD, DATABASE);
        // We need to accept the SQL Server End-User License Agreement (EULA):
        databaseService.withProperty("ACCEPT_EULA", "Y");

        return doInit(context, service, metadata.image(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }
}
