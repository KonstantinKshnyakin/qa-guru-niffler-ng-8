package guru.qa.niffler.api;

import jaxb.userdata.AllUsersRequest;
import jaxb.userdata.CurrentUserRequest;
import jaxb.userdata.UserResponse;
import jaxb.userdata.UsersResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface UserdataSoapClient {

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8"
    })
    @POST("ws")
    @Nonnull
    Call<UserResponse> currentUser(@Body CurrentUserRequest request);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8"
    })
    @POST("ws")
    @Nonnull
    Call<UsersResponse> allUsers(@Body AllUsersRequest request);
}