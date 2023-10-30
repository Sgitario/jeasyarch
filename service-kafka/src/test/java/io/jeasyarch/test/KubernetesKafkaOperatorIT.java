package io.jeasyarch.test;

import java.time.Duration;

import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import io.jeasyarch.api.Container;
import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.KafkaOperatorService;
import io.jeasyarch.api.KafkaResource;
import io.jeasyarch.api.Operator;
import io.jeasyarch.api.RestService;
import io.jeasyarch.api.RunOnKubernetes;

@JEasyArch
@RunOnKubernetes
@Operator(subscription = "strimzi-kafka-operator", channel = "strimzi-0.33.x")
public class KubernetesKafkaOperatorIT {

    @KafkaResource
    static final KafkaOperatorService kafka = new KafkaOperatorService();

    @Container(image = "${sample.messaging-kafka.image:server.io/test/quarkus-messaging-kafka:latest}", ports = 8080, expectedLog = "Installed features")
    static final RestService app = new RestService().withProperty("kafka.bootstrap.servers", kafka::getBootstrapUrl);

    @Test
    public void checkUserResourceByNormalUser() {
        Awaitility.await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            app.given().get("/prices/poll").then().statusCode(HttpStatus.SC_OK);
        });
    }
}
