package ru.netology.data;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class SqlRequest {
    private static QueryRunner runner = new QueryRunner();

    private static final String url = System.getProperty("db.url");

    private SqlRequest() {
    }

    @SneakyThrows
    public static Connection getConn() {

        return DriverManager.getConnection(url, "app", "pass");
    }

    @SneakyThrows
    public static String getDebitPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    public static String getCreditPaymentStatus() {
        String statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    private static String getResult(String query) {
        String result = "";
        var runner = new QueryRunner();
        try (var connection = getConn()) {
            result = runner.query(connection, query, new ScalarHandler<>());
        }
        return result;
    }

    @SneakyThrows
    public static void clearDB() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }
}