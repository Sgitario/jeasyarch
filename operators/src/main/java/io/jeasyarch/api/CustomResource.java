package io.jeasyarch.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.jeasyarch.api.model.CustomResourceSpec;
import io.jeasyarch.api.model.CustomResourceStatus;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomResource {

    String resource();

    Class<? extends io.fabric8.kubernetes.client.CustomResource<CustomResourceSpec, CustomResourceStatus>> type();
}
