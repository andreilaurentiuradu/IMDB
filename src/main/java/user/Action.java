package user;

import interaction.MenuBoard;
import interaction.TerminalInteraction;
import service.ProductionService;
import production.MediaIndustry;
import production.Production;
import production.details.Actor;
import production.details.Genre;
import service.ActorService;
import request.Request;
import request.RequestType;
import request.RequestsManager;
import service.RequestService;
import service.UserService;
import user.staff.Admin;
import user.staff.Contributor;
import user.staff.Staff;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Action {
    static TerminalInteraction terminalInteraction = new TerminalInteraction();

    ActorService actorService;
    ProductionService productionService;
    UserService userService;
    RequestService requestService;

    public Action(List<User> userList,
                  List<Actor> actorList,
                  List<Request> requestList,
                  List<Production> productionList) {
        actorService = new ActorService(actorList);
        productionService = new ProductionService(productionList);
        userService = new UserService(userList, requestList);
        requestService = new RequestService(requestList);
    }

    public void manageAdminUser(Admin currentUser) {
        boolean login = true;

        do {
            MenuBoard.showAdminActions();

            String action = terminalInteraction.readString("action");

            if (Integer.parseInt(action) < 6) {
                generalActions(action, currentUser);
            } else {
                switch (action) {
                    case "7":
                        resolveRequest(currentUser);
                        break;
                    case "9":
                        User user = createOrRemoveUserFromTerminal();
                        userService.addUser(user);

                        break;
                    case "10":
                        login = false;
                        break;
                    default:
                        throw new RuntimeException("Action not found");
                }
            }
        } while (login);
    }

    private void resolveRequest(Admin currentUser) {
        List<Request> availableRequests = visualizeResolvableRequests(currentUser.getUsername());

        Request requestToSolve = getCurrentRequest(availableRequests, "resolve");
        if (requestToSolve == null) {
            return;
        }

        System.out.println(requestToSolve);

        if (requestToSolve.getType() == RequestType.DELETE_ACCOUNT) {
            String deletedUsername = requestToSolve.getRequesterUsername();
            userService.printAllUsers();
            System.out.println();
            User deletedUser = userService.removeUser(deletedUsername);
            System.out.println("Removed user:" + deletedUser);
            requestToSolve.solved = true;

            if (deletedUser.getAccountType() == AccountType.REGULAR)
                return;

            Staff deletedStaff = (Staff) deletedUser;

            for (Request request : deletedStaff.requests) {
                requestService.addRequestForAdmin(request);
            }

            if (deletedUser.getAccountType() == AccountType.CONTRIBUTOR) {
                UserService.SUPREME.contributions.addAll(deletedStaff.contributions);
            }
            userService.printAllUsers();
            System.out.println();

            requestService.getAdminRequests();
        }

    }

    private List<Request> visualizeResolvableRequests(String adminName) {
        List<Request> availableRequests = new ArrayList<>();
        List<Request> adminRequests = requestService.getAdminRequests();
        List<Request> currentUserRequests = userService.findStaffByUsername(adminName).requests;

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

    private User createOrRemoveUserFromTerminal() {
        System.out.println("Introduce username");

        String username = terminalInteraction.readString("username");
        User user = userService.findUserByUsername(username);

        if (user == null) {
            user = getDataToCreateUser(username);
        } else {
            System.out.println("Deleting user");
        }

        return user;
    }

    private User getDataToCreateUser(String username) {
        User user;
        System.out.println("Creating user");

        System.out.println("Please introduce account type: Regular/Contributor/Admin");
        String accountTypeLabel = terminalInteraction.readString("accountType");
        AccountType accountType = AccountType.fromLabel(accountTypeLabel);
        user = new UserFactory().createUser(accountType);

        System.out.println("Please introduce email: ");
        String email = terminalInteraction.readString("email");
        System.out.println("Please introduce password: ");
        String password = terminalInteraction.readString("password");

        user.setCredentials(email, password);
        user.setUsername(username);
        user.setAccountType(accountType);
        return user;
    }

    public void manageContributorUser(Contributor currentUser) {
        boolean login;
        do {
            MenuBoard.showContributorActions();
            login = true;
            String action = terminalInteraction.readString("action");

            if (Integer.parseInt(action) < 6) {
                generalActions(action, currentUser);
            } else {
                switch (action) {
                    case "6":
                        System.out.println("Create/Discard request?");

                        action = terminalInteraction.readString("action");

                        if (action.equals("Create")) {
                            createRequest(currentUser, currentUser.getUsername());
                        } else {
                            cancelRequest(currentUser.getUsername());
                        }

                        requestService.getAdminRequests();
                        System.out.println();
                        break;
                    case "7":
                        break;
                    case "8":
                        break;
                    case "10":
                        login = false;
                        break;
                    default:
                        throw new RuntimeException("Action not found");
                }
            }
        } while (login);
    }

    private void cancelRequest(String username) {
        userService.printAllUsers();

        User currentUser = userService.findUserByUsername(username);
        List<Request> availableRequests = visualizeDeletableRequests(currentUser);

        Request requestToCancel = getCurrentRequest(availableRequests, "discard");

        if (requestToCancel == null) {
            return;
        }

        System.out.println(requestToCancel);

        userService.removeRequestFromResolverRequests(requestToCancel);
        requestService.removeAdminRequest(requestToCancel);

        currentUser.removeCreatedRequest(requestToCancel);

        userService.printAllUsers();
    }

    private Request getCurrentRequest(List<Request> availableRequests, String action) {
        System.out.println("Would you like to " + action + " a request? Enter request number or No");
        String resolveRequest = terminalInteraction.readString("request");

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

    public void manageRegularUser(Regular currentUser) {
        MenuBoard.showRegularActions();

        String action = terminalInteraction.readString("action");


        if(Integer.parseInt(action) < 6){
            generalActions(action, currentUser);
        } else {
            switch (action) {
                case "6":
                    System.out.println("Create/Discard request?");

                    action = terminalInteraction.readString("action");

                    if (action.equals("Create")) {
                        createRequest(currentUser, currentUser.getUsername());
                    } else {
                        cancelRequest(currentUser.getUsername());
                    }

                    requestService.getAdminRequests();
                    System.out.println();
                    break;
                case "7":
                    break;
                default:
                    throw new RuntimeException("Action not found");
            }
        }
    }

    private void createRequest(RequestsManager requestsManager, String username) {
        System.out.println("What kind of request?");
        String readType = terminalInteraction.readString("type");
        RequestType type = RequestType.valueOf(readType);

        System.out.println("Please describe the issue!");
        String readDescription = terminalInteraction.readString("description");

        LocalDateTime currentDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Request createdRequest = requestsManager.createRequest(type, readDescription, currentDate, username);
        String value;

        if (type == RequestType.MOVIE_ISSUE) {
            System.out.println("Movie title");
            value = terminalInteraction.readString("movie title");
            createdRequest.setProductionName(value);

            String resolverUsername = userService.findResolverByMediaTypeValue(value);
            createdRequest.setSolverUsername(resolverUsername);
            userService.addRequestToSolverRequestList(createdRequest);
        } else if (type == RequestType.ACTOR_ISSUE) {
            System.out.println("Actor name");
            value = terminalInteraction.readString("actor name");
            createdRequest.setActorName(value);

            String resolverUsername = userService.findResolverByMediaTypeValue(value);
            createdRequest.setSolverUsername(resolverUsername);
            userService.addRequestToSolverRequestList(createdRequest);
        } else if (type == RequestType.DELETE_ACCOUNT || type == RequestType.OTHERS) {
            requestService.addRequestForAdmin(createdRequest);
        } else {
            throw new RuntimeException("Invalid request type");
        }

        userService.addRequest(username, createdRequest);
        System.out.println(createdRequest);
    }

    public User identifyUser(Credentials credentials) {
        return userService.findUserByCredentials(credentials);
    }

    public Credentials getCurrentCredentials() {
        System.out.println("Welcome back! Enter your credentials!");

        String email, password;

        System.out.print("email: ");
        email = terminalInteraction.readString("email");

        System.out.print("password: ");
        password = terminalInteraction.readString("password");

        return new Credentials(email, password);
    }

    public void generalActions(String action, User currentUser) {
        switch (action) {
            case "1": {
                MenuBoard.showFilterOptions();

                action = terminalInteraction.readString("action");

                filterProductions(action, productionService);

                break;
            }
            case "2": {
                System.out.println("Do you want to sort them by name? Yes/No");
                String answer = terminalInteraction.readString("the answer");

                actorService.printActorDetails(answer);
                break;
            }
            case "3": {
                System.out.println(currentUser.getNotifications());
                break;
            }
            case "4": {
                System.out.println("What title/name do you want to search for?");
                String title = terminalInteraction.readString("title/name");

                System.out.println("Possible answers could be: ");
                actorService.searchByName(title);
                productionService.searchByTitle(title);
                break;
            }
            case "5": {
                System.out.println("Remove/Add");
                action = terminalInteraction.readString("action");

                System.out.println("What actor/production?");
                String title = terminalInteraction.readString("name/title");

                if (action.equals("Add")) {
                    currentUser.addMediaIndustry(new MediaIndustry(title));
                } else if (action.equals("Remove")) {
                    currentUser.removeMediaIndustry(new MediaIndustry(title));
                } else {
                    throw new RuntimeException("Action not found");
                }

                System.out.println("The new favorite list is:");
                System.out.println(currentUser.getFavorites());
                break;
            }
            default:
                throw new RuntimeException("This action doesn't exist");
        }
    }

    private void filterProductions(String action, ProductionService productionService) {
        switch (action) {
            case "1":
                System.out.println("What genre do you want?");
                String genre = terminalInteraction.readString("genre");

                productionService.printByGenre(Genre.valueOf(genre));
                break;

            case "2":
                System.out.println("Under/Over/Equal?");
                String type = terminalInteraction.readString("type");
                System.out.println(type + " which number?");

                String number = terminalInteraction.readString("number");

                System.out.println("By ratings:");
                productionService.printByRating(Double.valueOf(number), type);
                break;

            case "3":
                System.out.println("All:");
                break;

            default:
                throw new RuntimeException("action");
        }
    }
}
