package connection;

import by.melnikov.books.exception.ConnectionException;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionTest {
    @Mock
    private static HikariDataSource connectionPool;

    @Test
    void testGetConnectionThrowsException() throws SQLException {
        when(connectionPool.getConnection()).thenThrow(ConnectionException.class);
        assertThrows(ConnectionException.class, () -> connectionPool.getConnection());
    }
}
