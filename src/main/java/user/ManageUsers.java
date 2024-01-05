package user;

import production.MediaIndustry;
import production.Production;
import user.staff.Contributor;

import java.util.List;

public class ManageUsers {
    public List<User> users;

    public ManageUsers (List<User> users) {
        this.users = users;
    }

    public User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }

        return null;
    }

    public User findUserByCredentials(Credentials credentials) {
        for (User u : users) {
            if (credentials.validateCredentials(u.getInformation().getCredentials())) {
                return u;
            }
        }
        return null;
    }

    public String findResolverByMediaTypeValue(List<User> users, String value) {
        for (User user : users) {
            if (user.getAccountType() == AccountType.CONTRIBUTOR) {
                Contributor contributor = (Contributor) user;
                for (MediaIndustry mediaIndustry : contributor.contributions) {
                    if (mediaIndustry.value.equals(value))
                        return contributor.getUsername();
                }
            }
        }

        throw new RuntimeException("Can not find the owner of the resource");
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
