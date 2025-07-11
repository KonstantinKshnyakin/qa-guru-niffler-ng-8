package guru.qa.niffler.db.tpl;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcConnectionHolder implements AutoCloseable {

    private final DataSource dataSource;
    private final Map<Long, Connection> threadConnection = new ConcurrentHashMap<>();

    public JdbcConnectionHolder(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public @Nonnull Connection connection() {
        return threadConnection.computeIfAbsent(
                Thread.currentThread().threadId(),
                key -> {
                    try {
                        return dataSource.getConnection();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public void close() {
        Optional.ofNullable(threadConnection.remove(Thread.currentThread().threadId()))
                .ifPresent(connection -> {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException ignored) {
                    }
                });
    }

    public void closeAllConnections() {
        threadConnection.values().forEach(connection -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ignored) {
            }
        });
    }
}
