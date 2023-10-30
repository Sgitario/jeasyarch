package io.jeasyarch.resources.operators.kubernetes;

import io.jeasyarch.api.Operator;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.OperatorManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.KubernetesExtensionBootstrap;

public class KubernetesOperatorManagedResourceBinding implements OperatorManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return KubernetesExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, Operator metadata) {
        return new KubernetesOperatorManagedResource(metadata.subscription(), metadata.channel(), metadata.source(),
                metadata.sourceNamespace());
    }
}
