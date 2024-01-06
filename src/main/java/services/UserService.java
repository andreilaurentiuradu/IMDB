package services;

import repository.UserRepository;
import user.AccountType;
import user.Credentials;
import user.User;
import user.UserFactory;

import static services.ActionsService.terminalInteraction;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createOrRemoveUser() {
        String username = terminalInteraction.readString("Introduce username", "username");
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            user = getDataToCreateUser(username);
        } else {
            System.out.println("Deleting user");
        }

        userRepository.addUser(user);
    }

    private User getDataToCreateUser(String username) {
        User user;
        System.out.println("Creating user");

        String accountTypeLabel = terminalInteraction.readString("Introduce account type: Regular/Contributor/Admin", "accountType");
        AccountType accountType = AccountType.fromLabel(accountTypeLabel);
        user = new UserFactory().createUser(accountType);

        String email = terminalInteraction.readString("Please introduce email: ", "email");
        String password = terminalInteraction.readString("Please introduce password: ", "password");

        user.setCredentials(email, password);
        user.setUsername(username);
        user.setAccountType(accountType);
        return user;
    }

    public User identifyUser(Credentials credentials) {
        return userRepository.findUserByCredentials(credentials);
    }
}