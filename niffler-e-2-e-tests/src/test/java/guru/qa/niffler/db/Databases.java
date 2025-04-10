package guru.qa.niffler.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Databases {

    private Databases() {
    }

    private static final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    private static DataSource dataSource(String jdbcUrl) {
        return dataSources.computeIfAbsent(
                jdbcUrl,
                url -> {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
                    ds.setURL(url);
                    ds.setUser("postgres");
                    ds.setPassword("secret");
                    return ds;
                }
        );
    }

    public static Connection connection(String jdbcUrl) throws SQLException {
        return dataSource(jdbcUrl).getConnection();
    }

}
