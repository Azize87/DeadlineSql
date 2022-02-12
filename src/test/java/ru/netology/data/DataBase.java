package ru.netology.data;


import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;


public class DataBase {

    private QueryRunner runner;
    private Connection connection;

    @SneakyThrows
    public DataBase(String dbUrl, String user, String pass) {
        connection = DriverManager.getConnection(dbUrl, user, pass);
        runner = new QueryRunner();
    }

    @SneakyThrows
    public String getAuthCode(String user) {
        return runner.query(connection, "select code from auth_codes\n" +
                "join users u on u.id = auth_codes.user_id\n" +
                "where login = '" + user + "'\n" +
                "order by created desc", new ScalarHandler<>());
    }

    @SneakyThrows
    public String getAccountStatus(String user) {
        return runner.query(connection, "select status from users\n" +
                "where login = '" + user + "'", new ScalarHandler<>());
    }

    @SneakyThrows
    public void clearData() {
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
}
