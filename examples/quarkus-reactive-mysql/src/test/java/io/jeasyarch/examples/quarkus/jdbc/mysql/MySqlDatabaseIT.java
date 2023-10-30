package io.jeasyarch.examples.quarkus.jdbc.mysql;

import io.jeasyarch.api.DatabaseService;
import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.api.MySqlContainer;
import io.jeasyarch.api.Quarkus;
import io.jeasyarch.api.RestService;

@JEasyArch
public class MySqlDatabaseIT extends AbstractSqlDatabaseIT {

    @MySqlContainer
    static DatabaseService database = new DatabaseService();

    @Quarkus
    static RestService app = new RestService().withProperty("quarkus.datasource.username", database.getUser())
            .withProperty("quarkus.datasource.password", database.getPassword())
            .withProperty("quarkus.datasource.reactive.url", database::getReactiveUrl);
}
