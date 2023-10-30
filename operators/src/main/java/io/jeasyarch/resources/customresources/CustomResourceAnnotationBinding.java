package io.jeasyarch.resources.customresources;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.CustomResource;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.CustomResourceManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;

public class CustomResourceAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<CustomResourceManagedResourceBinding> bindings = ServiceLoader
            .load(CustomResourceManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, CustomResource.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        CustomResource metadata = findAnnotation(annotations, CustomResource.class).get();

        for (CustomResourceManagedResourceBinding binding : bindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata.resource(), metadata.type());
            }
        }

        throw new UnsupportedOperationException("Unsupported environment for @CustomResource service");
    }
}
