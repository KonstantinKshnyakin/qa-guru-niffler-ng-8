package guru.qa.niffler.util;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.model.*;

import java.util.List;

import static guru.qa.niffler.api.model.CurrencyValues.RUB;

public final class RandomDataUtils {

    private static final Faker FAKER = Faker.instance();
    public static final String USERNAME_PREFIX = "g_test_";

    private RandomDataUtils() {
        throw new UnsupportedOperationException();
    }

    public static String genPassword() {
        return FAKER.internet().password(3, 12);
    }

    public static String genUsername() {
        return USERNAME_PREFIX + FAKER.funnyName().name();
    }

    public static String genCategoryName() {
        return FAKER.app().name() + Thread.currentThread().threadId();
    }

    public static UserParts genDefaultUser(String username, String password) {
        List<AuthorityJson> defaultAuthorities = List.of(
                new AuthorityJson().setAuthority(Authority.write),
                new AuthorityJson().setAuthority(Authority.read)
        );
        AuthUserJson authUserJson = new AuthUserJson()
                .setUsername(username)
                .setPassword(password)
                .setEnabled(true)
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setCredentialsNonExpired(true)
                .setAuthorities(defaultAuthorities);
        UserdataUserJson userdataUserJson = new UserdataUserJson()
                .setUsername(username)
                .setCurrency(RUB);
        return new UserParts(authUserJson, userdataUserJson);
    }

    public static UserParts genDefaultUser() {
        return genDefaultUser(genUsername(), genPassword());
    }

    public static UserParts genDefaultUser(String password) {
        return genDefaultUser(genUsername(), password);
    }

}
