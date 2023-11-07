package io.jeasyarch.resources.quarkus;

import java.lang.annotation.Annotation;
import java.util.List;

import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.QuarkusManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.quarkus.local.ProdModeBootstrapQuarkusJavaProcessManagedResource;
import io.jeasyarch.utils.ServiceLoaderUtils;

public class QuarkusAnnotationBinding implements AnnotationBinding {

    private final List<QuarkusManagedResourceBinding> customBindings = ServiceLoaderUtils
            .load(QuarkusManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, Quarkus.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        Quarkus metadata = findAnnotation(annotations, Quarkus.class).get();

        for (QuarkusManagedResourceBinding binding : customBindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata);
            }
        }

        // If none handler found, then the container will be running on localhost by default
        return new ProdModeBootstrapQuarkusJavaProcessManagedResource(metadata.location(), metadata.classes(),
                metadata.dependencies(), metadata.forceBuild(), metadata.version());
    }

}
