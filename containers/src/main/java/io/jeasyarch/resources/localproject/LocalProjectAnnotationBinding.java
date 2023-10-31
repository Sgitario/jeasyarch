package io.jeasyarch.resources.localproject;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.LocalProject;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.LocalProjectManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.localproject.local.LocalProjectManagedGenericContainerManagedResource;

public class LocalProjectAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<LocalProjectManagedResourceBinding> bindings = ServiceLoader
            .load(LocalProjectManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, LocalProject.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        LocalProject metadata = findAnnotation(annotations, LocalProject.class).get();

        for (LocalProjectManagedResourceBinding binding : bindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata);
            }
        }

        // If none handler found, then the container will be running on localhost by default
        return new LocalProjectManagedGenericContainerManagedResource(metadata.location(), metadata.buildCommands(),
                metadata.dockerfile(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }

}
