package guru.qa.niffler.service.impl.api.soap;

import guru.qa.jaxb.userdata.*;
import guru.qa.niffler.api.ApiClients;
import guru.qa.niffler.api.UserdataSoapIClient;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import retrofit2.Response;

import javax.annotation.Nonnull;

public class UserdataSoapClient {

    private static UserdataSoapClient INSTANCE = new UserdataSoapClient();

    private UserdataSoapClient() {
    }

    public static UserdataSoapClient getInstance() {
        return INSTANCE;
    }

    private final UserdataSoapIClient userdataSoapClient = ApiClients.userdataSoapClient();

    @Step("SOAP отправка запроса 'currentUser'")
    public @Nonnull UserResponse currentUser(String username) {
        CurrentUserRequest request = new CurrentUserRequest();
        request.setUsername(username);
        return assertNotNull(userdataSoapClient.currentUser(request));
    }

    public @Nonnull UsersResponse friends(String username) {
        return friends(username, null);
    }

    @Step("SOAP отправка запроса 'friends'")
    public @Nonnull UsersResponse friends(String username, String searchQuery) {
        FriendsRequest request = new FriendsRequest();
        request.setUsername(username);
        request.setSearchQuery(searchQuery);
        return assertNotNull(userdataSoapClient.friends(request));
    }

    @Step("SOAP отправка запроса 'friendsPage'")
    public @Nonnull UsersResponse friendsPage(String username, String searchQuery, int page, int size) {
        FriendsPageRequest request = new FriendsPageRequest();
        request.setUsername(username);
        request.setSearchQuery(searchQuery);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setSize(size);
        request.setPageInfo(pageInfo);
        return assertNotNull(userdataSoapClient.friendsPage(request));
    }

    @Step("SOAP отправка запроса 'removeFriend'")
    public @Nonnull void removeFriend(String username, String friendToBeRemoved) {
        RemoveFriendRequest request = new RemoveFriendRequest();
        request.setUsername(username);
        request.setFriendToBeRemoved(friendToBeRemoved);
        userdataSoapClient.removeFriend(request);
    }

    @Step("SOAP отправка запроса 'declineInvitation'")
    public @Nonnull UserResponse declineInvitation(String username, String invitationToBeDeclined) {
        DeclineInvitationRequest request = new DeclineInvitationRequest();
        request.setUsername(username);
        request.setInvitationToBeDeclined(invitationToBeDeclined);
        return assertNotNull(userdataSoapClient.declineInvitation(request));
    }

    @Step("SOAP отправка запроса 'acceptInvitation'")
    public @Nonnull UserResponse acceptInvitation(String username, String friendToBeAdded) {
        AcceptInvitationRequest request = new AcceptInvitationRequest();
        request.setUsername(username);
        request.setFriendToBeAdded(friendToBeAdded);
        return assertNotNull(userdataSoapClient.acceptInvitation(request));
    }

    @Step("SOAP отправка запроса 'sendInvitation'")
    public @Nonnull UserResponse sendInvitation(String username, String friendToBeRequested) {
        SendInvitationRequest request = new SendInvitationRequest();
        request.setUsername(username);
        request.setFriendToBeRequested(friendToBeRequested);
        return assertNotNull(userdataSoapClient.sendInvitation(request));
    }

    private <T> @Nonnull T assertNotNull(@Nonnull Response<T> response) {
        T body = response.body();
        Assertions.assertNotNull(body);
        return body;
    }
}
