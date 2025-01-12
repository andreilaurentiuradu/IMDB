package services;

import exceptions.InvalidCommandException;
import org.apache.commons.lang3.RandomStringUtils;
import production.MediaIndustry;
import production.Production;
import production.details.Rating;
import repository.UserRepository;
import user.AccountType;
import user.Credentials;
import user.User;
import user.UserFactory;

import java.util.Comparator;
import java.util.List;

import static services.ActionsService.terminalInteraction;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createOrRemoveUser() {
        String option = terminalInteraction.readString("Create/Remove user?");

        if (option.equals("Create")) {
            User user = getDataToCreateUser();

            userRepository.addUser(user);
            user.displayNewUserInfo();
        } else if (option.equals("Remove")) {
            userRepository.printAllUsernames();

            String username = terminalInteraction.readString("Introduce username");
            User user = userRepository.findUserByUsername(username);

            userRepository.deleteUserDetails(user);
            System.out.println("Deleting user");
        } else {
            System.out.println("Invalid option! Please try again!");
        }
    }

    private User getDataToCreateUser() {
        String surname = terminalInteraction.readString("Introduce surname");
        String name = terminalInteraction.readString("Introduce name");

        String username = surname + "_" + name + RandomStringUtils.randomNumeric(5);

        User user;

        String accountTypeLabel = terminalInteraction.readString("Introduce account type: Regular/Contributor/Admin", "accountType");
        AccountType accountType = AccountType.fromLabel(accountTypeLabel);
        user = new UserFactory().createUser(accountType);

        String email = terminalInteraction.readString("Please introduce email:");

        String password = RandomStringUtils.randomAlphanumeric(20);

        user.setCredentials(email, password);
        user.setUsername(username);
        user.setAccountType(accountType);

        return user;
    }

    public User identifyUser(Credentials credentials) {
        return userRepository.findUserByCredentials(credentials);
    }

    private void printFavorites(User user) {
        System.out.println("The favorites list:");
        for (MediaIndustry mediaIndustry : user.getFavorites()) {
            System.out.println(mediaIndustry.value);
        }
        System.out.println();
    }

    public void manageFavorites(User currentUser) {
        printFavorites(currentUser);

        String action = terminalInteraction.readString("Remove/Add", "services");
        String value = terminalInteraction.readString("What actor/production?", "name/value");

        if (action.equals("Add")) {
            currentUser.addMediaIndustry(new MediaIndustry(value));
        } else if (action.equals("Remove")) {
            currentUser.removeMediaIndustry(new MediaIndustry(value));
        } else {
            System.out.println("Invalid action");
            return;
        }

        System.out.println("New favorites list:");
        printFavorites(currentUser);
    }


}
