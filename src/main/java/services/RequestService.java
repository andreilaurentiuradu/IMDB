package services;

import exceptions.InvalidCommandException;
import request.Request;
import request.RequestType;
import request.RequestsManager;
import repository.RequestRepository;
import repository.UserRepository;
import user.AccountType;
import user.User;
import user.staff.Admin;
import user.staff.Staff;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static interaction.MenuBoard.showResolveOthersOptions;
import static services.ActionsService.terminalInteraction;

public class RequestService {
    UserRepository userRepository;
    RequestRepository requestRepository;

    public RequestService(UserRepository userRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public void resolveRequest(Admin currentUser) {
        List<Request> availableRequests = getResolvableRequests(currentUser.getUsername());

        Request requestToSolve = getCurrentRequest(availableRequests, "resolve");
        if (requestToSolve == null) {
            return;
        }

        System.out.println(requestToSolve); // DEBUG

        String option = terminalInteraction.readString("Resolve/Delete", "option");

        switch (option) {
            case "Resolve":
                resolveByType(requestToSolve);
                requestToSolve.solved = true;
                break;
            case "Delete":
                ((RequestsManager) currentUser).removeRequest(requestToSolve);
                break;
            default:
                throw new InvalidCommandException("Invalid option");

        }
    }

    private void resolveByType(Request requestToSolve) {
        switch (requestToSolve.getType()) {
            case DELETE_ACCOUNT:
                rezolveDeleteAccountRequest(requestToSolve);
                break;
            case OTHERS:
                showResolveOthersOptions();
                int option = terminalInteraction.chosenOperation();
                if (option > 0 && option <= 3) {
                    rezolveOthersRequestForUser(requestToSolve, option);
                } else if (option > 3 && option < 6) {
                    rezolveOthersRequestForProduction(requestToSolve, option);
                } else {
                    throw new InvalidCommandException("Invalid resolve OTHERS option");
                }
        }
    }

    private void rezolveOthersRequestForProduction(Request requestToSolve, int option) {
        switch (option) {
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void rezolveOthersRequestForUser(Request requestToSolve, int option) {
        User userToUpdate = userRepository.findUserByUsername(requestToSolve.getRequesterUsername());
        if (userToUpdate == null) {
            System.out.println("User does not exist");
            return;
        }

        userToUpdate.displayInfo();
        switch (option) {
            case 1:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String birthDate = terminalInteraction.readString("Introduce birthday(yyyy-MM-dd)");
                LocalDate localDate = LocalDate.parse(birthDate, formatter);

                userToUpdate.updateBirthday(localDate);
                break;
            case 2:
                int age = terminalInteraction.readInt("Introduce age");
                userToUpdate.updateAge(age);
                break;
            case 3:
                String country = terminalInteraction.readString("Introduce country");
                userToUpdate.updateCountry(country);
                break;
        }
        System.out.println("After update: ");
        userToUpdate.displayInfo();
    }

    private void rezolveDeleteAccountRequest(Request requestToSolve) {
        String deletedUsername = requestToSolve.getRequesterUsername();
        userRepository.printAllUsernames();

        User deletedUser = userRepository.removeUser(deletedUsername);

        deleteUserDetails(deletedUser);
    }

    //    TODO delete
    private void deleteUserDetails(User deletedUser) {
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

    private List<Request> getResolvableRequests(String adminName) {
        List<Request> availableRequests = new ArrayList<>();
        List<Request> adminRequests = requestRepository.getAdminRequests();
        List<Request> currentUserRequests = userRepository.findStaffByUsername(adminName).requests;

        availableRequests.addAll(adminRequests);
        availableRequests.addAll(currentUserRequests);

        return printRequestsList(availableRequests);
    }

    private List<Request> visualizeDeletableRequests(User currentUser) {
        List<Request> deletableRequests = currentUser.getCreatedRequests();

        return printRequestsList(deletableRequests);
    }

    private List<Request> printRequestsList(List<Request> deletableRequests) {
        for (int i = 0; i < deletableRequests.size(); i++) {
            Request request = deletableRequests.get(i);
            if (!request.solved && !request.canceled) {
                System.out.print(i + 1 + ")");
                request.displayRequest();
            }
        }
        System.out.println();

        return deletableRequests;
    }

    public void createOrDiscardRequest(User currentUser) {
        String operation = terminalInteraction.readString("Create/Discard request?", "services");

        switch (operation) {
            case "Create":
                createRequest(currentUser);
                break;
            case "Discard":
                discardRequest(currentUser);
                break;
            default:
                throw new InvalidCommandException("Invalid operation");
        }
    }

    private void discardRequest(User currentUser) {
        List<Request> availableRequests = visualizeDeletableRequests(currentUser);
        Request requestToCancel = getCurrentRequest(availableRequests, "discard");

        if (requestToCancel == null) {
            return;
        }

        requestToCancel.displayRequest();

        ((RequestsManager) currentUser).removeRequest(requestToCancel);

        if (requestToCancel.getSolverUsername().equals("ADMIN"))
            requestRepository.removeAdminRequest(requestToCancel);

        currentUser.removeCreatedRequest(requestToCancel);
    }

    private Request getCurrentRequest(List<Request> availableRequests, String action) {
        System.out.println();
        String resolveRequest = terminalInteraction.readString(
                "Would you like to " + action + " a request? Enter request number or No",
                "request");

        if (resolveRequest.equals("No")) {
            return null;
        }

        int requestIndex = Integer.parseInt(resolveRequest) - 1;
        if (requestIndex < 0 || requestIndex > availableRequests.size() || availableRequests.get(requestIndex).solved) {
            System.out.println("Invalid request number");
            return null;
        }

        return availableRequests.get(requestIndex);
    }


    private void createRequest(User currentUser) {
        String readType = terminalInteraction.readString("What kind of request? " +
                "DELETE_ACCOUNT/ACTOR_ISSUE/MOVIE_ISSUE/OTHERS", "type");
        RequestType type = RequestType.getRequestType(readType);

        String readDescription = terminalInteraction.readString("Please describe the issue", "description");

        Request createdRequest = null;
        String value = null;
        String username = currentUser.getUsername();

        switch (type) {
            case DELETE_ACCOUNT:
            case OTHERS:
                createdRequest = ((RequestsManager) currentUser).createRequest(type, readDescription, username, null);
                requestRepository.addRequestForAdmin(createdRequest);
                userRepository.addRequestToUserCreatedRequestList(username, createdRequest);
                break;
            case ACTOR_ISSUE:
                value = terminalInteraction.readString("Introduce Actor name", "actor name");
            case MOVIE_ISSUE:
                if (value == null) {
                    value = terminalInteraction.readString("Introduce Movie title", "movie title");
                }

                createdRequest = ((RequestsManager) currentUser).createRequest(type, readDescription, username, value);

                Staff resolver = userRepository.findResolverByMediaTypeValue(value);

                if (resolver.getUsername().equals(username)) {
                    throw new RuntimeException("Can't request to yourself!");
                }

                createdRequest.setSolverUsername(resolver.getUsername());
                resolver.addRequest(createdRequest);
                currentUser.addCreatedRequest(createdRequest);
        }

        createdRequest.displayRequest();
    }
}
