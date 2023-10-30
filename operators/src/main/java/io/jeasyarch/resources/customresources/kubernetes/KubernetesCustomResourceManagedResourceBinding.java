package io.jeasyarch.resources.customresources.kubernetes;

import io.fabric8.kubernetes.client.CustomResource;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.CustomResourceManagedResourceBinding;
import io.jeasyarch.api.model.CustomResourceSpec;
import io.jeasyarch.api.model.CustomResourceStatus;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.KubernetesExtensionBootstrap;

public class KubernetesCustomResourceManagedResourceBinding implements CustomResourceManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return KubernetesExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, String resource,
            Class<? extends CustomResource<CustomResourceSpec, CustomResourceStatus>> type) {
        return new KubernetesCustomResourceManagedResource(resource, type);
    }
}
