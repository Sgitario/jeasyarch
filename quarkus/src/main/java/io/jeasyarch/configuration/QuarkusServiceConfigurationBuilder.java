package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class QuarkusServiceConfigurationBuilder
        extends BaseConfigurationBuilder<io.jeasyarch.api.QuarkusServiceConfiguration, QuarkusServiceConfiguration> {

    private static final String EXPECTED_OUTPUT = "quarkus.expected-log";
    private static final String DEPLOYMENT_METHOD = "quarkus.deployment-method";

    @Override
    public QuarkusServiceConfiguration build() {
        QuarkusServiceConfiguration config = new QuarkusServiceConfiguration();
        loadString(EXPECTED_OUTPUT, a -> a.expectedLog()).ifPresent(config::setExpectedLog);
        loadEnum(DEPLOYMENT_METHOD, DeploymentMethod.class, a -> a.deploymentMethod())
                .ifPresent(config::setDeploymentMethod);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.QuarkusServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.QuarkusServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
