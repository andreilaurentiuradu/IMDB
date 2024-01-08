package repository;

import production.Production;
import production.details.Actor;
import request.Request;
import user.AccountType;
import user.Credentials;
import user.Regular;
import user.User;
import user.staff.Admin;
import user.staff.Staff;

import java.util.ArrayList;
import java.util.List;

import static services.ActionsService.requestRepository;
import static services.ActionsService.userRepository;

public class UserRepository {
    private final List<User> users;
    private final List<Staff> staffList;

    public static final Admin SUPREME = new Admin("SUPREME_ADMIN");

    public UserRepository(List<User> users, List<Request> requests, List<Production> productions, List<Actor> actors) {

        this.users = users;
        this.staffList = new ArrayList<>();
        this.users.add(SUPREME);
        this.staffList.add(SUPREME);

        for (User user : users) {
            if (user.isStaff()) {
                staffList.add((Staff) user);
            }

            user.createMediaIndustryFavorites(actors, productions);
        }

        for (Request request : requests) {
            for (Staff staff : staffList) {
                if (staff.getUsername().equals(request.getSolverUsername())) {
                    staff.requests.add(request);
                }
            }

            User user = findUserByUsername(request.getRequesterUsername());
            user.addCreatedRequest(request);
        }

    }

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

    public void printAllUsers() {
        System.out.println("My users list");
        users.stream().filter(u -> u instanceof Staff).map(u -> (Staff) u).forEach(System.out::println);
        users.stream().filter(u -> u instanceof Regular).map(u -> (Regular) u).forEach(System.out::println);
        System.out.println("-----");
    }

    public void printAllUsernames() {
        System.out.println("Users list:");

        for (User user: users) {
            System.out.println(user.getUsername() + " " +
                    user.getInformation().getCredentials().getEmail() + " " +
                    user.getAccountType());
        }
        System.out.println();
    }

    public void addRequestToUserCreatedRequestList(String username, Request request) {
        User user = findUserByUsername(username);
        user.addCreatedRequest(request);
    }

    public User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }

        return null;
    }

    public Staff findStaffByUsername(String username) {
        for (Staff u : staffList) {
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

    public Staff findResolverByMediaTypeValue(String value) {
            for (User user : users) {
                if (user.isStaff()) {
                    Staff staff = (Staff) user;
                    if (staff.isContributorToMediaIndustry(value)) {
                        return staff;
                    }
                }
            }

            throw new RuntimeException("Can not find the owner of the resource");
    }

    public void addUser(User user) {
        if (user.isStaff()) {
            staffList.add((Staff) user);
        }

        users.add(user);
    }

    public void removeUser(User user) {
        if (user.isStaff()) {
            staffList.remove((Staff) user);
        }

        users.remove(user);
    }

    public User removeUser(String username) {
        User user = findUserByUsername(username);

        if (user.isStaff()) {
            staffList.remove((Staff) user);
        }

        users.remove(user);

        return user;
    }
}
