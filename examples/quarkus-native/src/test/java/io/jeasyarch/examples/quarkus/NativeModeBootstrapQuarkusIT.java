package io.jeasyarch.examples.quarkus;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.Dependency;
import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RestService;
import io.jeasyarch.examples.quarkus.samples.QuarkusPingApplication;

@Tag("native")
@JEasyArch
public class NativeModeBootstrapQuarkusIT {
    @Quarkus(dependencies = @Dependency(artifactId = "quarkus-resteasy"), classes = QuarkusPingApplication.class)
    static final RestService app = new RestService().withProperties("quarkus-ping-application.properties")
            .withProperty("quarkus.package.type", "native");

    @Test
    public void shouldExecuteAppInNativeMode() {
        app.given().get("/ping").then().body(is("pong"));
    }
}
