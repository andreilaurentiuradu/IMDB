package commander;

import exceptions.InvalidCommandException;
import services.ActionsService;
import helper.LoadData;
import production.Production;
import production.details.Actor;
import request.Request;
import user.*;
import user.staff.Admin;
import user.staff.Contributor;

import java.util.List;

public class IMDB {
    private static IMDB instance;
    List<User> users;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

    private final ActionsService action;

    private IMDB() {
        requests = LoadData.loadRequests();
        actors = LoadData.loadActors();
        productions = LoadData.loadProduction();
        users = LoadData.loadUsers();

       action = new ActionsService(users, actors, requests, productions);
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {
        User currentUser;
        currentUser = login();
        do {
            switch (currentUser.getAccountType()) {
                case REGULAR:
                    action.manageRegularUser((Regular) currentUser);
                    break;
                case CONTRIBUTOR:
                    action.manageContributorUser((Contributor)currentUser);
                    break;
                case ADMIN:
                    action.manageAdminUser((Admin) currentUser);
                    break;
                default:
                    throw new RuntimeException("AccountType not specified");
            }

            switch (action.exitOrRelogin(currentUser)) {
                case "Exit":
                    currentUser = null;
                    break;
                case "Logout":
                    currentUser = IMDB.getInstance().login();
                    break;
                default:
                    throw new InvalidCommandException("Invalid operation");
            }
        }while (currentUser != null);
    }

    public User login() {
        User currentUser;
        do {
            Credentials credentials = action.getCurrentCredentials();
            currentUser = action.identifyUser(credentials);

        } while(currentUser == null);

        // datele utilizatorului
        System.out.println("Welcome back user" + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());
        System.out.println(currentUser.getAccountType());
        return currentUser;
    }
}
