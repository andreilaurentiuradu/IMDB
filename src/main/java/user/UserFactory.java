package user;

import user.staff.Admin;
import user.staff.Contributor;

public class UserFactory {
    public User createUser(User user) {
        switch (user.getAccountType()) {
            case ADMIN:
                return new Admin();
            case REGULAR:
                return new Regular(user);
            case CONTRIBUTOR:
                return new Contributor(user);
            default:
                throw new RuntimeException("Invalid AccountType");
        }
    }
}