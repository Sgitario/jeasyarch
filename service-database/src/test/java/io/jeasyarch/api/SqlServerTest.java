package io.jeasyarch.api;

public class SqlServerTest extends BaseTest {
    @SqlServerContainer
    static final DatabaseService database = new DatabaseService();
}
