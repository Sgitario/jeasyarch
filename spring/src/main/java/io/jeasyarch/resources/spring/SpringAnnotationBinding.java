package io.jeasyarch.resources.spring;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.Service;
import io.jeasyarch.api.Spring;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.SpringManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.spring.local.LocalBootstrapSpringJavaProcessManagedResource;

public class SpringAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<SpringManagedResourceBinding> customBindings = ServiceLoader
            .load(SpringManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, Spring.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        Spring metadata = findAnnotation(annotations, Spring.class).get();

        for (SpringManagedResourceBinding binding : customBindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata);
            }
        }

        // If none handler found, then the container will be running on localhost by default
        return new LocalBootstrapSpringJavaProcessManagedResource(metadata.location(), metadata.forceBuild(),
                metadata.buildCommands());
    }

}
