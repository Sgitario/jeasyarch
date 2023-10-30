package io.jeasyarch.api.extensions;

import io.fabric8.kubernetes.client.CustomResource;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.model.CustomResourceSpec;
import io.jeasyarch.api.model.CustomResourceStatus;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;

public interface CustomResourceManagedResourceBinding {
    /**
     * @param context
     *
     * @return if the current managed resource applies for the current context.
     */
    boolean appliesFor(JEasyArchContext context);

    /**
     * Init and return the managed resource for the current context.
     *
     * @return
     */
    ManagedResource init(JEasyArchContext context, Service service, String resource,
                         Class<? extends CustomResource<CustomResourceSpec, CustomResourceStatus>> type);
}
