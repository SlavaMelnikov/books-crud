package by.melnikov.books.connection;

import by.melnikov.books.exception.ConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource connectionPool;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        config.setDataSourceClassName(resourceBundle.getString("db.driver"));
        config.setJdbcUrl(resourceBundle.getString("db.url"));
        config.setUsername(resourceBundle.getString("db.user"));
        config.setPassword(resourceBundle.getString("db.password"));
        connectionPool = new HikariDataSource(config);
    }

    private ConnectionPool() {
    }

    public static Connection getConnection(){
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Can't get connection: " + e.getMessage());
        }
    }

    /*
        Включает использование тестовых соединеный из testcontainers.
     */
    public static void turnOnTestContainersConnections(HikariDataSource testDataSource) {
        connectionPool = testDataSource;
    }

    /*
        Выключает использование тестовых соединеный.
    */
    public static void turnOffTestContainersConnections() {
        connectionPool = new HikariDataSource(config);
    }
}
