package io.jeasyarch.api;

public class MySqlTest extends BaseTest {
    @MySqlContainer
    static final DatabaseService database = new DatabaseService();
}
