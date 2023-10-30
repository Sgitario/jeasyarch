package io.jeasyarch.examples.quarkus.greetings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.QuarkusServiceConfiguration;
import io.jeasyarch.api.RestService;
import io.jeasyarch.api.ServiceConfiguration;
import io.jeasyarch.examples.quarkus.greetings.samples.QuarkusPingApplication;

@JEasyArch
@QuarkusServiceConfiguration(forService = "app", expectedLog = "this is wrong!")
@ServiceConfiguration(forService = "app", startupTimeout = "10s")
public class WrongConfigurationInAnnotationQuarkusIT {

    @Quarkus(classes = QuarkusPingApplication.class)
    static final RestService app = new RestService().withProperties("quarkus-ping-application.properties")
            .setAutoStart(false);

    @Test
    public void shouldFailBecauseExpectedLogIsWrong() {
        Assertions.assertThrows(RuntimeException.class, app::start,
                "Should fail because expected log in annotation is wrong");
    }
}
