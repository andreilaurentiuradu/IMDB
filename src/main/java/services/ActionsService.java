package services;

import commander.IMDB;
import exceptions.InvalidCommandException;
import interaction.MenuBoard;
import interaction.TerminalInteraction;
import production.Production;
import production.details.Actor;
import request.Request;
import repository.ActorRepository;
import repository.ProductionRepository;
import repository.RequestRepository;
import repository.UserRepository;
import user.Credentials;
import user.Regular;
import user.User;
import user.staff.Admin;
import user.staff.Contributor;

import java.util.List;

public class ActionsService {

    static TerminalInteraction terminalInteraction = new TerminalInteraction();

    GeneralService generalService;
    RequestService requestService;
    UserService userService;

    public ActionsService(List<User> users, List<Actor> actors, List<Request> requests, List<Production> productions) {

        ActorRepository actorRepository = new ActorRepository(actors);
        ProductionRepository productionRepository = new ProductionRepository(productions);
        UserRepository userRepository = new UserRepository(users, requests, productions, actors);
        RequestRepository requestRepository = new RequestRepository(requests);

        generalService = new GeneralService(actorRepository, productionRepository);
        requestService = new RequestService(userRepository, requestRepository);
        userService = new UserService(userRepository);
    }

    public void manageAdminUser(Admin currentUser) {
        boolean login = true;

        do {
            MenuBoard.showAdminActions();

            int action = terminalInteraction.chosenOperation();

            switch (action) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    generalActions(action, currentUser);
                    break;
                case 7:
                    requestService.resolveRequest(currentUser);
                    break;
                case 9:
                    userService.createOrRemoveUser();

                    break;
                case 10:
                    login = false;
                    break;
                default:
                    throw new RuntimeException("Action not found");

            }
        } while (login);
    }

    public void manageContributorUser(Contributor currentUser) {
        boolean login = true;
        do {
            MenuBoard.showContributorActions();
            int action = terminalInteraction.chosenOperation();

            switch (action) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    generalActions(action, currentUser);
                    break;
                case 6:
                    requestService.createOrDiscardRequest(currentUser, currentUser.getUsername());
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 10:
                    login = false;
                    break;
                default:
                    throw new InvalidCommandException("Action not found");

            }
        } while (login);
    }

    public void manageRegularUser(Regular currentUser) {
        boolean login = true;
        do {
            MenuBoard.showRegularActions();

            int action = terminalInteraction.chosenOperation();

            switch (action) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    generalActions(action, currentUser);
                    break;
                case 6:
                    requestService.createOrDiscardRequest(currentUser, currentUser.getUsername());
                    break;
                case 7:
                    break;
                case 10:
                    login = false;
                    break;
                default:
                    throw new InvalidCommandException("Action not found");
            }
        } while (login);
    }

    public void generalActions(int action, User currentUser) {
        switch (action) {
            case 1: {
                generalService.viewProductionDetails();
                break;
            }
            case 2: {
                generalService.viewActors();
                break;
            }
            case 3: {
                generalService.viewNotifications(currentUser);
                break;
            }
            case 4: {
                generalService.search();
                break;
            }
            case 5: {
                generalService.manageFavorites(currentUser);
                break;
            }
            default:
                throw new RuntimeException("This action doesn't exist");
        }
    }
    
    public Credentials getCurrentCredentials() {
        System.out.println("Welcome back! Enter your credentials!");

        String email, password;

        email = terminalInteraction.readString("email: ", "email");
        password = terminalInteraction.readString("password: ", "password");

        return new Credentials(email, password);
    }
    
    public User identifyUser(Credentials credentials) {
        return userService.identifyUser(credentials);
    }

    public String exitOrRelogin(User currentUser) {
        return terminalInteraction.readString("Exit/Logout?", "services");
    }
}