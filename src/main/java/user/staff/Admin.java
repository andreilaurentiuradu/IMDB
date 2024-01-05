package user.staff;

import user.AccountType;
import user.Credentials;
import user.User;

public class Admin extends Staff {
    public Admin() {

    }
    public Admin(User user) {
        super(user);
    }

    public Admin(String username) {
        super.setUsername(username);
        super.setCredentials("admin", "admin");
        super.setAccountType(AccountType.ADMIN);
    }
}
