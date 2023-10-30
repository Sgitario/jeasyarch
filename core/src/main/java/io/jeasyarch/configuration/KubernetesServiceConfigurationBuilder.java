package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class KubernetesServiceConfigurationBuilder extends
        BaseConfigurationBuilder<io.jeasyarch.api.KubernetesServiceConfiguration, KubernetesServiceConfiguration> {

    private static final String DEPLOYMENT_TEMPLATE_PROPERTY = "kubernetes.template";
    private static final String USE_INTERNAL_SERVICE_PROPERTY = "kubernetes.use-internal-service";
    private static final String ADDITIONAL_PORTS_PROPERTY = "kubernetes.additional-ports";
    private static final String SERVICE_ACCOUNT = "kubernetes.service-account";

    @Override
    public KubernetesServiceConfiguration build() {
        KubernetesServiceConfiguration serviceConfiguration = new KubernetesServiceConfiguration();
        loadString(DEPLOYMENT_TEMPLATE_PROPERTY, a -> a.template()).ifPresent(serviceConfiguration::setTemplate);
        loadBoolean(USE_INTERNAL_SERVICE_PROPERTY, a -> a.useInternalService())
                .ifPresent(serviceConfiguration::setUseInternalService);
        loadArrayOfIntegers(ADDITIONAL_PORTS_PROPERTY, a -> a.additionalPorts())
                .ifPresent(serviceConfiguration::setAdditionalPorts);
        loadString(SERVICE_ACCOUNT, a -> a.serviceAccount()).ifPresent(serviceConfiguration::setServiceAccount);
        return serviceConfiguration;
    }

    @Override
    protected Optional<io.jeasyarch.api.KubernetesServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.KubernetesServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
