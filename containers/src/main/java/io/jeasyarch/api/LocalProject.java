package io.jeasyarch.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalProject {
    String location();

    String[] buildCommands() default {};

    String dockerfile();

    int[] ports();

    String expectedLog() default "";

    String[] command() default {};
}
