package guru.qa.niffler.api.model;

import guru.qa.niffler.db.entity.auth.AuthUserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AuthUserJson {

    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private List<AuthorityJson> authorities = new ArrayList<>();

    public void addAuthorities(@Nonnull AuthorityJson... authorities) {
        List<AuthorityJson> authorityList = Arrays.stream(authorities)
                .peek(au -> au.setUserId(this.getId()))
                .toList();
        this.authorities.addAll(authorityList);
    }

    public void addAuthorities(@Nonnull AuthorityJson authority) {
        this.authorities.add(authority);
        authority.setUserId(this.getId());
    }

    public void removeAuthority(@Nullable AuthorityJson authority) {
        this.authorities.remove(authority);
    }

    public static @Nonnull AuthUserJson fromEntity(@Nonnull AuthUserEntity authEntity) {
        return new AuthUserJson()
                .setId(authEntity.getId())
                .setUsername(authEntity.getUsername())
                .setPassword("encrypted")
                .setEnabled(authEntity.getEnabled())
                .setAccountNonExpired(authEntity.getAccountNonExpired())
                .setAccountNonLocked(authEntity.getAccountNonLocked())
                .setCredentialsNonExpired(authEntity.getCredentialsNonExpired())
                .setAuthorities(AuthorityJson.fromEntity(authEntity.getAuthorities()));
    }
}
