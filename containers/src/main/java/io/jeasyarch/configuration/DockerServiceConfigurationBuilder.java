package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class DockerServiceConfigurationBuilder
        extends BaseConfigurationBuilder<io.jeasyarch.api.DockerServiceConfiguration, DockerServiceConfiguration> {

    private static final String PRIVILEGED_MODE = "docker.privileged-mode";

    @Override
    public DockerServiceConfiguration build() {
        DockerServiceConfiguration config = new DockerServiceConfiguration();
        loadBoolean(PRIVILEGED_MODE, a -> a.privileged()).ifPresent(config::setPrivileged);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.DockerServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.DockerServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
