package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class OpenShiftServiceConfigurationBuilder extends
        BaseConfigurationBuilder<io.jeasyarch.api.OpenShiftServiceConfiguration, OpenShiftServiceConfiguration> {

    private static final String DEPLOYMENT_TEMPLATE_PROPERTY = "openshift.template";
    private static final String USE_INTERNAL_SERVICE_PROPERTY = "openshift.use-internal-service";
    private static final String ADDITIONAL_PORTS_PROPERTY = "openshift.additional-ports";
    private static final String USE_ROUTE = "openshift.use-route";
    private static final String SERVICE_ACCOUNT = "openshift.service-account";

    @Override
    public OpenShiftServiceConfiguration build() {
        OpenShiftServiceConfiguration config = new OpenShiftServiceConfiguration();
        loadString(DEPLOYMENT_TEMPLATE_PROPERTY, a -> a.template()).ifPresent(config::setTemplate);
        loadBoolean(USE_INTERNAL_SERVICE_PROPERTY, a -> a.useInternalService())
                .ifPresent(config::setUseInternalService);
        loadArrayOfIntegers(ADDITIONAL_PORTS_PROPERTY, a -> a.additionalPorts()).ifPresent(config::setAdditionalPorts);
        loadBoolean(USE_ROUTE, a -> a.useRoute()).ifPresent(config::setUseRoute);
        loadString(SERVICE_ACCOUNT, a -> a.serviceAccount()).ifPresent(config::setServiceAccount);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.OpenShiftServiceConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.OpenShiftServiceConfiguration.class,
                a -> a.forService().equals(serviceName));
    }
}
