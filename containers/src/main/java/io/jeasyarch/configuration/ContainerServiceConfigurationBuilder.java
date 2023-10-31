package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class ContainerServiceConfigurationBuilder extends
        BaseConfigurationBuilder<io.jeasyarch.api.ContainerServiceConfiguration, ContainerServiceConfiguration> {

    private static final String PRIVILEGED_MODE = "container.privileged-mode";
    private static final String IMAGE = "container.image";

    @Override
    public ContainerServiceConfiguration build() {
        ContainerServiceConfiguration config = new ContainerServiceConfiguration();
        loadBoolean(PRIVILEGED_MODE, a -> a.privileged()).ifPresent(config::setPrivileged);
        loadString(IMAGE, a -> a.image()).ifPresent(config::setImage);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.ContainerServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.ContainerServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
