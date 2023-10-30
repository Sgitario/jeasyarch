package io.jeasyarch.resources.containers.kubernetes;

import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.ContainerManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.KubernetesExtensionBootstrap;

public class KubernetesContainerManagedResourceBinding implements ContainerManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return KubernetesExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, String image, String expectedLog,
            String[] command, int[] ports) {
        return new KubernetesContainerManagedResource(image, expectedLog, command, ports);
    }
}
