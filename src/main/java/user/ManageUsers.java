package user;

import java.util.ArrayList;
import java.util.List;

public class ManageUsers {
    public List<User> users = new ArrayList<>();

    public User findUserByCredentials(Credentials credentials) {
        for (User u : users) {
            if (credentials.validateCredentials(u.getInformation().getCredentials())) {
                return u;
            }
        }
        return null;
    }
}
