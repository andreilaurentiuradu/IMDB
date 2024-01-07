package services;

import org.apache.commons.lang3.RandomStringUtils;
import production.MediaIndustry;
import repository.RequestRepository;
import repository.UserRepository;
import request.Request;
import user.AccountType;
import user.Credentials;
import user.User;
import user.UserFactory;
import user.staff.Staff;

import java.util.Random;

import static services.ActionsService.terminalInteraction;

public class UserService {

    UserRepository userRepository;
    RequestRepository requestRepository;

    public UserService(UserRepository userRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public void createOrRemoveUser() {
        String username = terminalInteraction.readString("Introduce username", "username");
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            user = getDataToCreateUser(username);
            if (userRepository.findUserByUsername(username) != null) {
                System.out.println("Username already exists");
                return;
            }

            userRepository.addUser(user);
        } else {
            userRepository.printAllUsernames();

            deleteUserDetails(user);
            System.out.println("Deleting user");
        }

    }

    private User getDataToCreateUser(String username) {
        User user;
        System.out.println("Creating user");

        String accountTypeLabel = terminalInteraction.readString("Introduce account type: Regular/Contributor/Admin", "accountType");
        AccountType accountType = AccountType.fromLabel(accountTypeLabel);
        user = new UserFactory().createUser(accountType);

        String email = terminalInteraction.readString("Please introduce email: ", "email");

        String password = RandomStringUtils.randomAlphanumeric(20);
        System.out.println("Generated password is: " + password);

        user.setCredentials(email, password);
        user.setUsername(username);
        user.setAccountType(accountType);
        return user;
    }

    public User identifyUser(Credentials credentials) {
        return userRepository.findUserByCredentials(credentials);
    }

    public void printFavorites(User user) {
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
            throw new RuntimeException("Action not found");
        }

        System.out.println("The new favorite list is:"); // TODO debug
        printFavorites(currentUser);// TODO debug
    }

//    TODO delete
    public void deleteUserDetails(User deletedUser) {
        userRepository.removeUser(deletedUser);

        for (Request createdRequest : deletedUser.getCreatedRequests()) {
            createdRequest.canceled = true;
        }

        userRepository.printAllUsernames();

        if (deletedUser.getAccountType() == AccountType.REGULAR) {
            return;
        }

        Staff deletedStaff = (Staff) deletedUser;

        for (Request request : deletedStaff.requests) {
            requestRepository.addRequestForAdmin(request);
        }

        UserRepository.SUPREME.getContributions().addAll(deletedStaff.getContributions());
        requestRepository.getAdminRequests().forEach(System.out::println); // DEBUG
    }
}
