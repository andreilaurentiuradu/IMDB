package service;

import production.MediaIndustry;
import request.Request;
import user.Credentials;
import user.User;
import user.staff.Admin;
import user.staff.Staff;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users;
    private final List<Staff> staffList;

    public static final Admin SUPREME = new Admin("SUPREME_ADMIN");

    public UserService(List<User> users, List<Request> requests) {

        this.users = users;
        this.staffList = new ArrayList<>();
        this.users.add(SUPREME);
        this.staffList.add(SUPREME);

        for (User user : users) {
            if (user.isStaff()) {
                staffList.add((Staff) user);
            }
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

    public void printAllUsers() {
        System.out.println("My users list");
        users.forEach(System.out::println);
        System.out.println("-----");
    }

    public void addRequestToSolverRequestList(Request request) {
        Staff staffResolver = (Staff) findUserByUsername(request.getSolverUsername());
        staffResolver.addRequest(request);
    }

    public void addRequest(String username, Request request) {
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

    public String findResolverByMediaTypeValue(String value) {
        for (User user : users) {
            if (user.isStaff()) {
                Staff staff = (Staff) user;
                for (MediaIndustry mediaIndustry : staff.contributions) {
                    if (mediaIndustry.value.equals(value))
                        return staff.getUsername();
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

    public User removeUser(String username) {
        User user = findUserByUsername(username);

        if (user.isStaff()) {
            staffList.remove((Staff) user);
        }

        users.remove(user);

        return user;
    }

    public void removeRequestFromResolverRequests(Request requestToCancel) {
        Staff staff = findStaffByUsername(requestToCancel.getSolverUsername());
        if (staff == null)
            return;
        staff.requests.remove(requestToCancel);
    }
}
