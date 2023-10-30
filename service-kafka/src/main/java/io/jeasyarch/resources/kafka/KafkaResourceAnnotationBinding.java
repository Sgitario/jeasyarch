package io.jeasyarch.resources.kafka;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.KafkaResource;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.CustomResourceManagedResourceBinding;
import io.jeasyarch.api.model.KafkaInstanceCustomResource;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;

public class KafkaResourceAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<CustomResourceManagedResourceBinding> bindings = ServiceLoader
            .load(CustomResourceManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, KafkaResource.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        KafkaResource metadata = findAnnotation(annotations, KafkaResource.class).get();

        for (CustomResourceManagedResourceBinding binding : bindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata.resource(), KafkaInstanceCustomResource.class);
            }
        }

        throw new UnsupportedOperationException("Unsupported environment for @Operator service");
    }
}
