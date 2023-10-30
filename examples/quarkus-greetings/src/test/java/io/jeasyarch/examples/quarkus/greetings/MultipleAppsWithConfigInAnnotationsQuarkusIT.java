package io.jeasyarch.examples.quarkus.greetings;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.QuarkusServiceConfiguration;
import io.jeasyarch.api.RestService;
import io.jeasyarch.examples.quarkus.greetings.samples.AppLifecycleBean;
import io.jeasyarch.examples.quarkus.greetings.samples.QuarkusPingApplication;

@JEasyArch
@QuarkusServiceConfiguration(forService = "one", expectedLog = "Installed features: (.*), resteasy-reactive, (.*)")
@QuarkusServiceConfiguration(forService = "two", expectedLog = "Custom log at startup event!")
public class MultipleAppsWithConfigInAnnotationsQuarkusIT {

    @Quarkus(classes = QuarkusPingApplication.class)
    static final RestService one = new RestService().withProperties("quarkus-ping-application.properties");

    @Quarkus(classes = { QuarkusPingApplication.class, AppLifecycleBean.class })
    static final RestService two = new RestService().withProperties("quarkus-ping-application.properties");

    @Test
    public void shouldExecuteAppInProdMode() {
        one.given().get("/ping").then().body(Matchers.is("pong"));
        two.given().get("/ping").then().body(Matchers.is("pong"));
    }
}
