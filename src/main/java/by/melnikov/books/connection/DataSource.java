package by.melnikov.books.connection;

import by.melnikov.books.exception.ConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource connectionPool;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        config.setDataSourceClassName(resourceBundle.getString("db.driver"));
        config.setJdbcUrl(resourceBundle.getString("db.url"));
        config.setUsername(resourceBundle.getString("db.user"));
        config.setPassword(resourceBundle.getString("db.password"));
        connectionPool = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection(){
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Can't get connection: " + e.getMessage());
        }
    }
}
