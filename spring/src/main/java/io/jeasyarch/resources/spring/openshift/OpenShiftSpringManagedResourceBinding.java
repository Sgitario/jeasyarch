package io.jeasyarch.resources.spring.openshift;

import io.jeasyarch.api.Service;
import io.jeasyarch.api.Spring;
import io.jeasyarch.api.extensions.SpringManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.OpenShiftExtensionBootstrap;

public class OpenShiftSpringManagedResourceBinding implements SpringManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return OpenShiftExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, Spring metadata) {
        return new ContainerRegistrySpringOpenShiftManagedResource(metadata.location(), metadata.forceBuild(),
                metadata.buildCommands());
    }
}
