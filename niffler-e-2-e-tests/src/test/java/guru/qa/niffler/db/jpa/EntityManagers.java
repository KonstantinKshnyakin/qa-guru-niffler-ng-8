package guru.qa.niffler.db.jpa;

import guru.qa.niffler.db.tpl.DataSources;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManagers {

    public EntityManagers() {
    }

    private static final Map<String, EntityManagerFactory> emfs = new ConcurrentHashMap<>();

    public static @Nonnull EntityManager em(@Nonnull String jdbcUrl) {
        return new ThreadSafeEntityManager(
            emfs.computeIfAbsent(
                jdbcUrl,
                url -> {
                    DataSources.dataSource(jdbcUrl);
                    return Persistence.createEntityManagerFactory(jdbcUrl);
                }
            ).createEntityManager()
        );
    }

    public static void closeAllEmfs() {
        emfs.values().forEach(EntityManagerFactory::close);
    }
}
