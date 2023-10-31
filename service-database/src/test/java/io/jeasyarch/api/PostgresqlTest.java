package io.jeasyarch.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class PostgresqlTest extends BaseTest {
    @PostgresqlContainer
    static final DatabaseService database = new DatabaseService();

    @Test
    public void shouldBeAbleToRunQueries() {
        database.openStatement(statement -> {
            try {
                assertTrue(statement.execute("select 1"));
            } catch (SQLException e) {
                fail("Query failed with:" + e.getMessage());
            }
        });
    }
}
