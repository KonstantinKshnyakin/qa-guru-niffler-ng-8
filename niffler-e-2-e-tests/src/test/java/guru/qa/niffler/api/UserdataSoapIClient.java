package guru.qa.niffler.api;

import guru.qa.jaxb.userdata.*;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface UserdataSoapIClient {

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8"
    })
    @POST("ws")
    @Nonnull
    Response<UserResponse> currentUser(@Body CurrentUserRequest request);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8"
    })
    @POST("ws")
    @Nonnull
    Response<UsersResponse> allUsers(@Body AllUsersRequest request);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<UsersResponse> friends(@Body FriendsRequest friendsRequest);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<UsersResponse> friendsPage(@Body FriendsPageRequest friendsRequest);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<Void> removeFriend(@Body RemoveFriendRequest removeFriendRequest);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<UserResponse> declineInvitation(@Body DeclineInvitationRequest declineInvitationRequest);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<UserResponse> acceptInvitation(@Body AcceptInvitationRequest acceptInvitationRequest);

    @Headers(value = {
        "Content-type: text/xml",
        "Accept-Charset: utf-8",
    })
    @POST("ws")
    Response<UserResponse> sendInvitation(@Body SendInvitationRequest sendInvitationRequest);
}