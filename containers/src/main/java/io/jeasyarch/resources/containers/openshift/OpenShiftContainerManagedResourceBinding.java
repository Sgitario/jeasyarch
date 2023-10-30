package io.jeasyarch.resources.containers.openshift;

import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.ContainerManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.OpenShiftExtensionBootstrap;

public class OpenShiftContainerManagedResourceBinding implements ContainerManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return OpenShiftExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, String image, String expectedLog,
            String[] command, int[] ports) {
        return new OpenShiftContainerManagedResource(image, expectedLog, command, ports);
    }
}
