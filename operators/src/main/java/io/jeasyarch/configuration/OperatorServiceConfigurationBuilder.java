package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class OperatorServiceConfigurationBuilder
        extends BaseConfigurationBuilder<io.jeasyarch.api.OperatorServiceConfiguration, OperatorServiceConfiguration> {

    private static final String INSTALL_TIMEOUT = "operator.install.timeout";

    @Override
    public OperatorServiceConfiguration build() {
        OperatorServiceConfiguration config = new OperatorServiceConfiguration();
        loadDuration(INSTALL_TIMEOUT, a -> a.installTimeout()).ifPresent(config::setInstallTimeout);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.OperatorServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.OperatorServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
