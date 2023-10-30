package io.jeasyarch.examples.quarkus.openshift;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RunOnOpenShift;

@JEasyArch
@Quarkus
@RunOnOpenShift
public class OpenShiftDefaultQuarkusIT {
    @Test
    public void testDefaultRestServiceIsUpAndRunning() {
        given().get("/greeting").then().statusCode(HttpStatus.SC_OK).body(is("Hello, I'm victor"));
    }
}
