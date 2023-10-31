package io.jeasyarch.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@JEasyArch
public abstract class BaseTest {

    @LookupService
    DatabaseService database;

    @Test
    public void shouldBeUpAndRunning() {
        Assertions.assertTrue(database.isRunning());
    }
}
