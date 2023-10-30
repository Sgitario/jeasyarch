package io.jeasyarch.api.extensions;

import io.jeasyarch.api.Service;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;

public interface ContainerManagedResourceBinding {
    /**
     * @param context
     *
     * @return if the current managed resource applies for the current context.
     */
    boolean appliesFor(JEasyArchContext context);

    /**
     * Init and return the managed resource for the current context.
     */
    ManagedResource init(JEasyArchContext context, Service service, String image, String expectedLog, String[] command,
            int[] ports);
}
