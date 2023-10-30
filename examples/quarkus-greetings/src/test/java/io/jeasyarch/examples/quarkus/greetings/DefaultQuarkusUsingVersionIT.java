package io.jeasyarch.examples.quarkus.greetings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RestService;

@JEasyArch
public class DefaultQuarkusUsingVersionIT {

    @Quarkus(version = "2.15.2.Final")
    RestService app = new RestService();

    @Test
    public void testDefaultRestServiceIsUpAndRunningAndUsingVersion() {
        given().get("/greeting").then().statusCode(HttpStatus.SC_OK).body(is("Hello, I'm victor"));
        app.logs().assertContains("2.15.2.Final");
    }
}
