package io.jeasyarch.resources.quarkus.openshift;

import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.QuarkusManagedResourceBinding;
import io.jeasyarch.configuration.DeploymentMethod;
import io.jeasyarch.configuration.QuarkusServiceConfiguration;
import io.jeasyarch.configuration.QuarkusServiceConfigurationBuilder;
import io.jeasyarch.configuration.ServiceConfigurationLoader;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.OpenShiftExtensionBootstrap;
import io.jeasyarch.utils.QuarkusUtils;

public class OpenShiftQuarkusManagedResourceBinding implements QuarkusManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return OpenShiftExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, Quarkus metadata) {
        QuarkusServiceConfiguration config = ServiceConfigurationLoader.load(service.getName(), context,
                new QuarkusServiceConfigurationBuilder());
        if (config.getDeploymentMethod() == DeploymentMethod.USING_EXTENSION
                || (config.getDeploymentMethod() == DeploymentMethod.AUTO
                        && QuarkusUtils.isKubernetesExtensionLoaded())) {
            return new UsingExtensionQuarkusOpenShiftManagedResource(metadata.location());
        }

        return new ContainerRegistryProdModeBootstrapQuarkusOpenShiftManagedResource(metadata.location(),
                metadata.classes(), metadata.dependencies(), metadata.forceBuild(), metadata.version());
    }
}
