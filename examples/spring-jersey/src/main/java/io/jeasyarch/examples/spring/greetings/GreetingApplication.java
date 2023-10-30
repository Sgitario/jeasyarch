package io.jeasyarch.examples.spring.greetings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class GreetingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GreetingApplication.class, args);
    }
}
