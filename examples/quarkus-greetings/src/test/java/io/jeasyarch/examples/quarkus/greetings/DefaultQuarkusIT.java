package io.jeasyarch.examples.quarkus.greetings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;

@JEasyArch
@Quarkus
public class DefaultQuarkusIT {

    @Test
    public void testDefaultRestServiceIsUpAndRunning() {
        given().get("/greeting").then().statusCode(HttpStatus.SC_OK).body(is("Hello, I'm victor"));
    }
}
