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

    private final Action action;
    private final UserFactory userFactory = new UserFactory();

    private IMDB() {
        requests = LoadData.loadRequests();
        actors = LoadData.loadActors();
        productions = LoadData.loadProduction();
        users = LoadData.loadUsers();

        for (User user : users) {
            user.createFavorites(actors, productions);
        }

       action = new Action(users, actors, requests, productions);
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {

        // autentificarea utilizatorului

        User currentUser = login();

        switch (currentUser.getAccountType()) {
            case REGULAR:
                Regular regular = (Regular) userFactory.createUser(currentUser);
                action.manageRegularUser(regular);
                break;
            case CONTRIBUTOR:
                Contributor contributor = (Contributor) userFactory.createUser(currentUser);
                action.manageContributorUser(contributor);
                break;
            case ADMIN:
                Admin admin = (Admin) userFactory.createUser(currentUser);
                action.manageAdminUser(admin);
                break;
            default:
                throw new RuntimeException("AccountType not specified");
        }
    }

    private User login() {
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
