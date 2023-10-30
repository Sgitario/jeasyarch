package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class SpringServiceConfigurationBuilder
        extends BaseConfigurationBuilder<io.jeasyarch.api.SpringServiceConfiguration, SpringServiceConfiguration> {

    private static final String EXPECTED_OUTPUT = "spring.expected-log";

    @Override
    public SpringServiceConfiguration build() {
        SpringServiceConfiguration config = new SpringServiceConfiguration();
        loadString(EXPECTED_OUTPUT, a -> a.expectedLog()).ifPresent(config::setExpectedLog);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.SpringServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.SpringServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
