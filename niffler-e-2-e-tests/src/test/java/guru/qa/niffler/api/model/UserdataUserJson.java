package guru.qa.niffler.api.model;

import guru.qa.niffler.db.entity.userdata.UserdataUserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserdataUserJson {

    private UUID id;
    private String username;
    private CurrencyValues currency;
    private String firstname;
    private String surname;
    private String fullname;
    private FriendshipStatus friendshipStatus;
    private byte[] photo;
    private byte[] photoSmall;

    public static @Nonnull UserdataUserJson fromEntity(@Nonnull UserdataUserEntity userdataEntity) {
        return new UserdataUserJson()
                .setId(userdataEntity.getId())
                .setUsername(userdataEntity.getUsername())
                .setCurrency(userdataEntity.getCurrency())
                .setFirstname(userdataEntity.getFirstname())
                .setSurname(userdataEntity.getSurname())
                .setFullname(userdataEntity.getFullname())
                .setPhoto(userdataEntity.getPhoto())
                .setPhotoSmall(userdataEntity.getPhotoSmall());
    }

    public enum FriendshipStatus {
        INVITE_SENT,
        INVITE_RECEIVED,
        FRIEND,
        VOID;
    }
}
