package io.jeasyarch.examples.spring.greetings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Spring;

@JEasyArch
@Spring
public class GreetingApplicationIT {

    @Test
    public void testSpringApp() {
        given().get().then().body(is("Hello World"));
    }
}
