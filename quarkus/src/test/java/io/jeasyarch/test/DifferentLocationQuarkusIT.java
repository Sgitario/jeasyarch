package io.jeasyarch.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.ServiceConfiguration;

@JEasyArch
@Quarkus(location = "../images/quarkus-rest")
@ServiceConfiguration(forService = "quarkus", deleteFolderOnClose = false)
public class DifferentLocationQuarkusIT {

    @Test
    public void shouldExecuteAppInProdMode() {
        given().get("/hello").then().statusCode(HttpStatus.SC_OK).body(is("Hello Samples"));
    }
}
