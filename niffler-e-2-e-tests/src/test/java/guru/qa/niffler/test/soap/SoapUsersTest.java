package guru.qa.niffler.test.soap;

import guru.qa.niffler.api.ApiClients;
import guru.qa.niffler.api.UserdataSoapClient;
import guru.qa.niffler.api.model.UserParts;
import guru.qa.niffler.jupiter.annotation.SoapTest;
import guru.qa.niffler.jupiter.annotation.User;
import jaxb.userdata.CurrentUserRequest;
import jaxb.userdata.UserResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

@SoapTest
public class SoapUsersTest {

    private final UserdataSoapClient userdataSoapClient = ApiClients.userdataSoapClient();

    @SneakyThrows
    @Test
    @User
    public void currentUserTest(UserParts user) {
        CurrentUserRequest request = new CurrentUserRequest();
        String username = user.getUsername();
        request.setUsername(username);
        Response<UserResponse> execute = userdataSoapClient.currentUser(request).execute();
        UserResponse body = execute.body();
        Assertions.assertEquals(username, body.getUser().getUsername());
    }
}
