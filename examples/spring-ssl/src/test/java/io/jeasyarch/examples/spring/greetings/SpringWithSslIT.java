package io.jeasyarch.examples.spring.greetings;

import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.RestService;
import io.jeasyarch.api.ServiceConfiguration;
import io.jeasyarch.api.Spring;

@JEasyArch
@ServiceConfiguration(forService = "app", deleteFolderOnClose = false)
public class SpringWithSslIT {

    @Spring
    RestService app = new RestService();

    @Test
    public void testHttps() {
        app.https().get().then().statusCode(HttpStatus.SC_OK).body(is("Hello ssl!"));
    }
}
