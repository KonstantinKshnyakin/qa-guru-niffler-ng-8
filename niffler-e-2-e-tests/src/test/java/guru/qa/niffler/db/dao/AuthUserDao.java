package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.entity.auth.AuthUserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthUserDao {

    AuthUserEntity create(AuthUserEntity entity);

    AuthUserEntity update(AuthUserEntity entity);

    Optional<AuthUserEntity> findById(UUID id);

    Optional<AuthUserEntity> findByUsername(String username);

    boolean delete(AuthUserEntity entity);

    List<AuthUserEntity> findAll();

}
