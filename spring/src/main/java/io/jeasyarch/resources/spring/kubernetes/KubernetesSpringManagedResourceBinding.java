package io.jeasyarch.resources.spring.kubernetes;

import io.jeasyarch.api.Service;
import io.jeasyarch.api.Spring;
import io.jeasyarch.api.extensions.SpringManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.KubernetesExtensionBootstrap;

public class KubernetesSpringManagedResourceBinding implements SpringManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return KubernetesExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, Spring metadata) {
        return new ContainerRegistrySpringKubernetesManagedResource(metadata.location(), metadata.forceBuild(),
                metadata.buildCommands());
    }
}
