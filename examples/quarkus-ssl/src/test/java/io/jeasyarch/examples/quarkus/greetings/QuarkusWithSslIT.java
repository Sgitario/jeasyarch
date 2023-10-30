package io.jeasyarch.examples.quarkus.greetings;

import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RestService;
import io.jeasyarch.utils.Ports;

@JEasyArch
public class QuarkusWithSslIT {

    @Quarkus(classes = { GreetingResource.class, CustomNoopHostnameVerifier.class })
    RestService app = new RestService().withProperties("server.properties");

    @Quarkus(classes = { Client.class, ClientResource.class, CustomNoopHostnameVerifier.class })
    RestService client = new RestService().withProperties("client.properties").withProperty(
            "quarkus.rest-client.client.url", () -> "https://localhost:" + app.getMappedPort(Ports.DEFAULT_SSL_PORT));

    @Test
    public void testHttps() {
        app.https().get("/greeting").then().statusCode(HttpStatus.SC_OK).body(is("Hello ssl!"));
    }

    @Test
    public void testHttpsInRestClient() {
        client.given().get("/greeting-from-client").then().statusCode(HttpStatus.SC_OK).body(is("Hello ssl!"));
    }
}
