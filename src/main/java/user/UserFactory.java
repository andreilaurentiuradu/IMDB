package user;

import user.staff.Admin;
import user.staff.Contributor;

public class UserFactory {
    public User createUser(AccountType type) {
        switch (type) {
            case ADMIN:
                return new Admin();
            case REGULAR:
                return new Regular();
            case CONTRIBUTOR:
                return new Contributor();
            default:
                throw new RuntimeException("Invalid AccountType");
        }
    }
}
