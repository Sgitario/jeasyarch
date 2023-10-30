package io.jeasyarch.utils;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import io.jeasyarch.api.Service;

public class LogsVerifier {

    private final Service service;

    public LogsVerifier(Service service) {
        this.service = service;
    }

    public void assertContains(String expectedLog) {
        assertContains(expectedLog, AwaitilitySettings.defaults());
    }

    public void assertContains(String expectedLog, AwaitilitySettings settings) {
        AwaitilityUtils.untilAsserted(() -> {
            List<String> actualLogs = service.getLogs();
            Assertions.assertTrue(actualLogs.stream().anyMatch(line -> line.contains(expectedLog)),
                    "Log does not contain " + expectedLog + ". Full logs: " + actualLogs);
        }, settings);
    }

    public void assertDoesNotContain(String unexpectedLog) {
        List<String> actualLogs = service.getLogs();
        Assertions.assertTrue(actualLogs.stream().noneMatch(line -> line.contains(unexpectedLog)),
                "Log does contain " + unexpectedLog + ". Full logs: " + actualLogs);
    }
}
