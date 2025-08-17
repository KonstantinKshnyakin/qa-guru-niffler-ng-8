package guru.qa.niffler.test.soap;

import guru.qa.jaxb.userdata.UserResponse;
import guru.qa.jaxb.userdata.UsersResponse;
import guru.qa.niffler.api.model.UserParts;
import guru.qa.niffler.jupiter.annotation.SoapTest;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.service.impl.api.soap.UserdataSoapClient;
import guru.qa.niffler.service.impl.db.UserDbClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static guru.qa.jaxb.userdata.FriendshipStatus.INVITE_RECEIVED;
import static guru.qa.niffler.util.RandomDataUtils.genDefaultUser;

@SoapTest
public class SoapUsersTest {

    private final UserdataSoapClient userdataSoapClient = UserdataSoapClient.getInstance();
    private final UserDbClient userDbClient = new UserDbClient();

    @Test
    @User
    public void currentUserTest(UserParts userParts) {
        String username = userParts.getUsername();

        UserResponse body = userdataSoapClient.currentUser(username);
        Assertions.assertEquals(username, body.getUser().getUsername());
    }

    @Test
    @User(withFriend = 3)
    public void friendsPageShouldBePresent(UserParts userParts) {
        UsersResponse body = userdataSoapClient.friendsPage(userParts.getUsername(), null, 0, 10);
        Assertions.assertEquals(1, body.getTotalPages());
        Assertions.assertEquals(userParts.getTestData().getFriendsNames().size(), body.getTotalElements());
    }

    @Test
    @User(withFriend = 3)
    public void friendsPageShouldBeFiltered(UserParts userParts) {
        String username = userParts.getTestData().getFriendsNames().getFirst();
        UsersResponse body = userdataSoapClient.friendsPage(userParts.getUsername(), username, 0, 10);

        Assertions.assertEquals(1, body.getTotalPages());
        Assertions.assertEquals(1, body.getTotalElements());
        Assertions.assertFalse(body.getUser().isEmpty());
        Assertions.assertEquals(username, body.getUser().getFirst().getUsername());
    }

    @Test
    @User(withFriend = 3)
    public void friendsPageShouldBeFilteredByNamePrefix(UserParts userParts) {
        UsersResponse body = userdataSoapClient.friendsPage(userParts.getUsername(), "g_test", 0, 10);
        Assertions.assertNotNull(body.getUser());
        Assertions.assertEquals(3, body.getUser().size());
        Assertions.assertEquals(1, body.getTotalPages());
        Assertions.assertEquals(3, body.getTotalElements());
    }

    @Test
    @User(withFriend = 1)
    public void friendshipMustBeDeleted(UserParts userParts) {
        String friendToBeRemoved = userParts.getTestData().getFriendsNames().getFirst();
        userdataSoapClient.removeFriend(userParts.getUsername(), friendToBeRemoved);

        UsersResponse body = userdataSoapClient.friends(userParts.getUsername(), null);
        Assertions.assertNotNull(body.getUser());
        Assertions.assertTrue(body.getUser().isEmpty());
    }

    @Test
    @User(withInInvite = 1)
    public void acceptIncomeInvitationTest(UserParts userParts) {
        String friendToBeAdded = userParts.getTestData().getInInviteNames().getFirst();
        userdataSoapClient.acceptInvitation(userParts.getUsername(), friendToBeAdded);

        UsersResponse body = userdataSoapClient.friends(userParts.getUsername());
        Assertions.assertNotNull(body.getUser());
        Assertions.assertEquals(1, body.getUser().size());
        Assertions.assertEquals(friendToBeAdded, body.getUser().getFirst().getUsername());
    }

    @Test
    @User(withInInvite = 1)
    void friendRequestMustBeDeclined(UserParts userParts) {
        String invitationToBeDeclined = userParts.getTestData().getInInviteNames().getFirst();
        userdataSoapClient.declineInvitation(userParts.getUsername(), invitationToBeDeclined);

        UsersResponse body = userdataSoapClient.friends(userParts.getUsername());
        Assertions.assertNotNull(body.getUser());
        Assertions.assertTrue(body.getUser().isEmpty());
    }

    @Test
    @User
    void afterSendInvitationMustBeOutRequest(UserParts userParts) {
        String friendToBeRequested = userDbClient.createUser(genDefaultUser()).getUsername();
        userdataSoapClient.sendInvitation(userParts.getUsername(), friendToBeRequested);

        UsersResponse body = userdataSoapClient.friends(friendToBeRequested);
        Assertions.assertNotNull(body.getUser());
        Assertions.assertFalse(body.getUser().isEmpty());
        Assertions.assertEquals(INVITE_RECEIVED, body.getUser().getFirst().getFriendshipStatus());
    }
}
