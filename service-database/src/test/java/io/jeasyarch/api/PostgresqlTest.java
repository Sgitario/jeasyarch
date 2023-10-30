package io.jeasyarch.api;

public class PostgresqlTest extends BaseTest {
    @PostgresqlContainer
    static final DatabaseService database = new DatabaseService();
}
