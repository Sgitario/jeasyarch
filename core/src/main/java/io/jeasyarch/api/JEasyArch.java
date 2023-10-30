package io.jeasyarch.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import io.jeasyarch.core.JEasyArchExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(JEasyArchExtension.class)
@Inherited
public @interface JEasyArch {
    /**
     * Set the target environment where to run the tests. Fallback property `ts.jeasyarch.target`.
     */
    String target() default "local";

    /**
     * Enable profiling only for Java processes. Fallback property `ts.jeasyarch.enable.profiling`.
     */
    boolean enableProfiling() default false;
}
