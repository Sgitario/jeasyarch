package io.jeasyarch.examples.quarkus.greetings;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RestService;
import io.jeasyarch.examples.quarkus.greetings.samples.QuarkusPingApplication;

@JEasyArch
public class ProdModeBootstrapQuarkusIT {
    @Quarkus(classes = QuarkusPingApplication.class)
    static final RestService app = new RestService().withProperties("quarkus-ping-application.properties");

    @Test
    public void shouldExecuteAppInProdMode() {
        app.given().get("/ping").then().body(Matchers.is("pong"));
    }
}
