package io.jeasyarch.resources.containers;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.Container;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.ContainerManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.containers.local.DefaultGenericContainerManagedResource;

public class ContainerAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<ContainerManagedResourceBinding> containerBindings = ServiceLoader
            .load(ContainerManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, Container.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        Container metadata = findAnnotation(annotations, Container.class).get();

        return doInit(context, service, metadata.image(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }

    protected ManagedResource doInit(JEasyArchContext context, Service service, String image, String expectedLog,
            String[] command, int[] ports) {
        for (ContainerManagedResourceBinding binding : containerBindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, image, expectedLog, command, ports);
            }
        }

        // If none handler found, then the container will be running on localhost by default
        return new DefaultGenericContainerManagedResource(image, expectedLog, command, ports);
    }

}
