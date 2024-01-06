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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static services.ActionsService.terminalInteraction;

public class RequestService {
    UserRepository userRepository;
    RequestRepository requestRepository;

    public RequestService(UserRepository userRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public void resolveRequest(Admin currentUser) {
        List<Request> availableRequests = visualizeResolvableRequests(currentUser.getUsername());

        Request requestToSolve = getCurrentRequest(availableRequests, "resolve");
        if (requestToSolve == null) {
            return;
        }

        System.out.println(requestToSolve);

        if (requestToSolve.getType() == RequestType.DELETE_ACCOUNT) {
            String deletedUsername = requestToSolve.getRequesterUsername();
            userRepository.printAllUsers();
            System.out.println();
            User deletedUser = userRepository.removeUser(deletedUsername);
            System.out.println("Removed user:" + deletedUser);
            requestToSolve.solved = true;

            if (deletedUser.getAccountType() == AccountType.REGULAR)
                return;

            Staff deletedStaff = (Staff) deletedUser;

            for (Request request : deletedStaff.requests) {
                requestRepository.addRequestForAdmin(request);
            }

            if (deletedUser.getAccountType() == AccountType.CONTRIBUTOR) {
                UserRepository.SUPREME.contributions.addAll(deletedStaff.contributions);
            }
            userRepository.printAllUsers();
            System.out.println();

            requestRepository.getAdminRequests();
        }

    }

    private List<Request> visualizeResolvableRequests(String adminName) {
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
            if (!request.solved) {
                System.out.println(i + 1 + ")" + request);
            }
        }
        System.out.println();

        return deletableRequests;
    }

    public void createOrDiscardRequest(RequestsManager currentUser, String username) {
        String operation = terminalInteraction.readString("Create/Discard request?", "services");

        switch (operation) {
            case "Create":
                createRequest(currentUser, username);
                break;
            case "Discard":
                cancelRequest(username);
                break;
            default:
                throw new InvalidCommandException("Invalid operation");
        }
    }

    private void cancelRequest(String username) {
        User currentUser = userRepository.findUserByUsername(username);
        List<Request> availableRequests = visualizeDeletableRequests(currentUser);
        Request requestToCancel = getCurrentRequest(availableRequests, "discard");

        if (requestToCancel == null) {
            return;
        }

        System.out.println(requestToCancel);

        userRepository.removeRequestFromResolverRequests(requestToCancel);
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


    private void createRequest(RequestsManager requestsManager, String username) {
        String readType = terminalInteraction.readString("What kind of request?", "type");
        RequestType type = RequestType.getRequestType(readType);

        String readDescription = terminalInteraction.readString("Please describe the issue", "description");

        LocalDateTime currentDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Request createdRequest;

        if (requestsManager instanceof Admin) {
            createdRequest = requestsManager.createRequest(type, readDescription, currentDate, username, null);
            requestRepository.addRequestForAdmin(createdRequest);
        } else {
            String value =  terminalInteraction.readString("Introduce Movie title/ Actor name", "movie title");
            createdRequest = requestsManager.createRequest(type, readDescription, currentDate, username, value);

            String resolverUsername = userRepository.findResolverByMediaTypeValue(value);
            createdRequest.setSolverUsername(resolverUsername);
            userRepository.addRequestToSolverRequestList(createdRequest);
        }

        userRepository.addRequest(username, createdRequest);
        System.out.println(createdRequest);
    }

}
