package guru.qa.niffler.db.dao.impl.jdbc;

import guru.qa.niffler.api.model.CurrencyValues;
import guru.qa.niffler.db.dao.UserdataUserDao;
import guru.qa.niffler.db.entity.userdata.UserdataUserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserdataUserDaoJdbc extends AbstractDao<UserdataUserEntity> implements UserdataUserDao {

    public UserdataUserDaoJdbc(String jdbcUrl) {
        super(jdbcUrl);
    }

    @Override
    public UserdataUserEntity create(UserdataUserEntity entity) {
        String sql = "INSERT INTO \"user\" (username, currency, firstname, surname, full_name, photo, photo_small) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING *";
        return executeQuery(sql, entity.getUsername(), entity.getCurrency().name(), entity.getFirstname(),
                entity.getSurname(), entity.getFullname(), entity.getPhoto(), entity.getPhotoSmall());
    }

    @Override
    public UserdataUserEntity update(UserdataUserEntity entity) {
        String sql = "UPDATE \"user\" SET username = ?, currency = ?, firstname = ?, surname = ?, full_name = ?, " +
            "photo = ?, photo_small = ? WHERE id = ? RETURNING *";
        return executeQuery(sql, entity.getUsername(), entity.getCurrency().name(), entity.getFirstname(),
            entity.getSurname(), entity.getFullname(), entity.getPhoto(), entity.getPhotoSmall(), entity.getId());
    }

    @Override
    public Optional<UserdataUserEntity> findById(UUID id) {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";
        return executeQueryToOptional(sql, id);
    }

    @Override
    public Optional<UserdataUserEntity> findByUsername(String username) {
        String sql = "SELECT * FROM \"user\" WHERE username = ?";
        return executeQueryToOptional(sql, username);
    }

    @Override
    public boolean delete(UserdataUserEntity entity) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";
        return executeUpdateToBoolean(sql, entity.getId());
    }

    @Override
    public List<UserdataUserEntity> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return executeQueryToList(sql);
    }

    @Override
    protected UserdataUserEntity mapResultSet(ResultSet rs) throws SQLException {
        return new UserdataUserEntity()
                .setId(rs.getObject("id", UUID.class))
                .setUsername(rs.getString("username"))
                .setCurrency(CurrencyValues.valueOf(rs.getString("currency")))
                .setFirstname(rs.getString("firstname"))
                .setSurname(rs.getString("surname"))
                .setFullname(rs.getString("full_name"))
                .setPhoto(rs.getBytes("photo"))
                .setPhotoSmall(rs.getBytes("photo_small"));
    }

}