package services;

import exceptions.InvalidCommandException;
import production.Movie;
import production.details.Actor;
import repository.RequestRepository;
import repository.UserRepository;
import request.Request;
import request.RequestType;
import request.RequestsManager;
import user.AccountType;
import user.User;
import user.experience.SolvedRequestExperienceStrategy;
import user.notifications.Observer;
import user.staff.Staff;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static interaction.MenuBoard.showResolveOthersOptions;
import static services.ActionsService.terminalInteraction;
import static services.ProductionService.updateMovie;

public class RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    public RequestService(UserRepository userRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public void resolveRequest(Staff currentUser) {
        List<Request> availableRequests = currentUser.getResolvableRequests();

        Request requestToSolve = getCurrentRequest(availableRequests, "resolve");
        if (requestToSolve == null) {
            return;
        }

        System.out.println(requestToSolve); // DEBUG

        String option = terminalInteraction.readString("Resolve/Delete", "option");

        switch (option) {
            case "Resolve":
                resolveByType(requestToSolve, currentUser);
                requestToSolve.solved = true;
                requestToSolve.notifyObservers("The request with description " + requestToSolve.getDescription() + " WAS SOLVED by " +currentUser.getUsername());
                break;
            case "Delete":
                ((RequestsManager) currentUser).removeRequest(requestToSolve);
                requestToSolve.notifyObservers("The request with description " + requestToSolve.getDescription() + " WAS DELETED by " + currentUser.getUsername());
                break;
            default:
                throw new InvalidCommandException("Invalid option");
        }
    }

    private void resolveByType(Request requestToSolve, Staff currentUser) {
        switch (requestToSolve.getType()) {
            case DELETE_ACCOUNT:
                rezolveDeleteAccountRequest(requestToSolve);
                break;
            case OTHERS:
                showResolveOthersOptions();
                int option = terminalInteraction.chosenOperation();

                System.out.println("Description: " + requestToSolve.getDescription());

                if (option > 0 && option <= 3) {
                    rezolveOthersRequestForUser(requestToSolve, option);
                } else if (option > 3 && option < 6) {
                    rezolveOthersRequestForProduction(currentUser, option);
                } else {
                    throw new InvalidCommandException("Invalid resolve OTHERS option");
                }

                requestToSolve.solved = true;
                break;
            case MOVIE_ISSUE:
                updateMovie(requestToSolve, currentUser);
                User MOVIE_ISSUE_requester = userRepository.findUserByUsername(requestToSolve.getRequesterUsername());
                MOVIE_ISSUE_requester.addExperience(new SolvedRequestExperienceStrategy());
                break;
            case ACTOR_ISSUE:
                Actor newActor = updateActorField(requestToSolve.getActorName());
                if (newActor != null) {
                    currentUser.updateActor(newActor);
                    User ACTOR_ISSUE_requester = userRepository.findUserByUsername(requestToSolve.getRequesterUsername());
                    ACTOR_ISSUE_requester.addExperience(new SolvedRequestExperienceStrategy());
                }
        }
    }

    public static Actor updateActorField(String name) {
        System.out.println("Which field do you want to update? ");
        String whichField = terminalInteraction.readString("Choose one from: Performances/Biography");

        Actor update = new Actor(name);

        switch (whichField) {
            case "Performances":
                String productionType = terminalInteraction.readString("Movie/Series?");
                String productionName = terminalInteraction.readString("Introduce " + productionType + " title");

                update.addPerformances(productionName, productionType);
                return update;
            case "Biography":
                String updateField = terminalInteraction.readString("Introduce biography details");
                update.updateBiography(updateField);
                return update;
            default:
                System.out.println("Invalid input.");
        }

        return null;
    }

    private void rezolveOthersRequestForProduction(Staff currentUser, int option) {
        String value = null;

        switch (option) {
            case 4:
                value = terminalInteraction.readString("Introduce movie title for adding");
                currentUser.addProductionSystem(new Movie(value));
                break;
            case 5:
                value = terminalInteraction.readString("Introduce actor name for adding");
                currentUser.addActorSystem(new Actor(value));
                break;
        }

        currentUser.addContribution(value);
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

        userRepository.deleteUserDetails(deletedUser);
    }

    private List<Request> visualizeDeletableRequests(User currentUser) {
        List<Request> deletableRequests = currentUser.getCreatedRequests();

        return currentUser.printRequestsList(deletableRequests);
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

        requestToCancel.notifyObservers("The request with description " + requestToCancel.getDescription() + ", WAS CANCELED");
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

    private void notifyAllAdmins(Request request) {
        for (User user : userRepository.getUsersList()) {
            if (user.getAccountType() == AccountType.ADMIN)
                request.addObserver(user);
        }
        request.notifyObservers("Admins have a new request from " + request.getRequesterUsername());
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

                notifyAllAdmins(createdRequest);
                createdRequest.addObserver(currentUser);
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

                createdRequest.addObserver(resolver);
                createdRequest.notifyObservers("You have a new request from " + createdRequest.getRequesterUsername());
                createdRequest.addObserver(currentUser);

                createdRequest.setSolverUsername(resolver.getUsername());
                resolver.addRequest(createdRequest);
                currentUser.addCreatedRequest(createdRequest);
        }


        createdRequest.displayRequest();
    }
}
