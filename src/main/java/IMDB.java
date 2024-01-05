import helper.LoadData;
import production.ManageProduction;
import production.MediaIndustry;
import production.Production;
import production.details.Actor;
import production.details.Genre;
import production.details.ManageActors;
import request.Request;
import request.RequestType;
import request.RequestsManager;
import user.*;
import user.staff.Contributor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class IMDB {
    private static IMDB instance;
    List<User> users;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

    static TerminalInteraction terminalInteraction = new TerminalInteraction();

    ManageActors manageActors;
    ManageProduction manageProduction;
    ManageUsers manageUsers;

    private IMDB() {
        requests = LoadData.loadRequests();

        actors = LoadData.loadActors();
        manageActors = new ManageActors(actors);

        productions = LoadData.loadProduction();
        manageProduction = new ManageProduction(productions);

        users = LoadData.loadUsers();
        manageUsers = new ManageUsers(users);

        for (User user : users) {
            user.createFavorites(actors, productions);
        }
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {

        // autentificarea utilizatorului
        UserFactory userFactory = new UserFactory();

        User currentUser;

        do {
            Credentials credentials = getCurrentCredentials();
            currentUser = manageUsers.findUserByCredentials(credentials);

        } while(currentUser == null);

        // datele utilizatorului
        System.out.println("Welcome back user" + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());
        System.out.println(currentUser.getAccountType());

        showGeneralActions();

        switch (currentUser.getAccountType()) {
            case REGULAR:
                Regular regular = (Regular) userFactory.createUser(currentUser);
                manageRegularUser(regular);
                break;
            case CONTRIBUTOR:
                Contributor contributor = (Contributor) userFactory.createUser(currentUser);
                manageContributorUser(manageActors, manageProduction, contributor);
                break;
            case ADMIN:
                manageAdminUser(currentUser);
                break;
            default:
                throw new RuntimeException("AccountType not specified");
        }
    }

    private void manageAdminUser(User currentUser) {
        String action = terminalInteraction.readString("action");
        System.out.println("action: " + action);

        generalActions(action, manageActors, manageProduction, currentUser);
    }

    private void manageContributorUser(ManageActors manageActors,
                                       ManageProduction manageProduction,
                                       Contributor currentUser) {
        System.out.println("6) Create/Discard a request");

        String action = terminalInteraction.readString("action");
        System.out.println("action: " + action);

        if(Integer.parseInt(action) < 6){
            generalActions(action, manageActors, manageProduction, currentUser);
        } else {
            switch (action) {
                case "6":
                    readCreateOrDiscardRequest(currentUser, currentUser.getUsername());
                    break;
                case "7":
                    break;
                case "8":
                    break;
                default:
                    throw new RuntimeException("Action not found");
            }
        }
    }

    private void manageRegularUser(Regular currentUser) {
        System.out.println("6) Create/Discard a request");
        System.out.println("7) Add/Delete review");

        String action = terminalInteraction.readString("action");
        System.out.println("action: " + action);

        if(Integer.parseInt(action) < 6){
            generalActions(action, manageActors, manageProduction, currentUser);
        } else {
            switch (action) {
                case "6":
                    readCreateOrDiscardRequest(currentUser, currentUser.getUsername());
                    break;
                case "7":
                    break;
                default:
                    throw new RuntimeException("Action not found");
            }
        }
    }

    private void readCreateOrDiscardRequest(RequestsManager currentUser, String username) {
        System.out.println("Create/Discard?");

        String action = terminalInteraction.readString("action");
        System.out.println("action: " + action);

        if (action.equals("Create")) {
            Request createdRequest = createRequest(currentUser, username);
            System.out.println(createdRequest);

            String value;

            if (createdRequest.getType() == RequestType.MOVIE_ISSUE) {
                System.out.println("Movie title");
                value = terminalInteraction.readString("movie title");

            } else if (createdRequest.getType() == RequestType.ACTOR_ISSUE) {
                System.out.println("Actor name");
                value = terminalInteraction.readString("actor name");
            } else {
                throw new RuntimeException("Invalid request type");
            }


            String resolverUsername = manageUsers.findResolverByMediaTypeValue(users, value);
            createdRequest.setSolverUsername(resolverUsername);

            System.out.println(resolverUsername);
            System.out.println(createdRequest);
        }
    }


    private Request createRequest(RequestsManager requestsManager, String username) {
        System.out.println("What kind of request?");

        String readType = terminalInteraction.readString("type");
        System.out.println("Please describe the issue!");

        String readDescription = terminalInteraction.readString("description");

        LocalDateTime currentDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        return requestsManager.createRequest(readType, readDescription, currentDate, username);
    }

    private static void showGeneralActions() {
        System.out.println("Choose action by pressing the correspondent number:");
        System.out.println("1) View productions details");
        System.out.println("2) View actors details");
        System.out.println("3) View notifications");
        System.out.println("4) Search for Actor/Movie/Series");
        System.out.println("5) Remove/Add Production/Actor from/to favorites List");
    }

    private static Credentials getCurrentCredentials() {
        System.out.println("Welcome back! Enter your credentials!");

        String email, password;

        System.out.print("email: ");
        email = terminalInteraction.readString("email");

        System.out.print("password: ");
        password = terminalInteraction.readString("password");

        return new Credentials(email, password);
    }

    public void generalActions(String action,
                               ManageActors manageActors,
                               ManageProduction manageProduction,
                               User currentUser) {
        switch (action) {
            case "1": {
                System.out.println("Filter results by:");
                System.out.println("1) Genre");
                System.out.println("2) Ratings");
                System.out.println("3) Don't filter");

                action = terminalInteraction.readString("action");
                System.out.println("action: " + action);

                filterByAction(action, manageProduction);

                break;
            }
            case "2": {
                System.out.println("Do you want to sort them by name? Yes/No");
                String answer = terminalInteraction.readString("Can't read the answer");

                manageActors.printActorDetails(answer);
                break;
            }
            case "3": {
                System.out.println(currentUser.getNotifications());
                break;
            }
            case "4": {
                System.out.println("What title/name do you want to search for?");
                String title = terminalInteraction.readString("Can't read the title/name");

                System.out.println("Possible answers could be: ");
                manageActors.searchByName(title);
                manageProduction.searchByTitle(title);
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

    private void filterByAction(String action, ManageProduction manageProduction) {
        switch (action) {
            case "1":
                System.out.println("What genre do you want?");
                String genre = terminalInteraction.readString("genre");

                manageProduction.printByGenre(Genre.valueOf(genre));
                break;

            case "2":
                System.out.println("Under/Over/Equal?");
                String type = terminalInteraction.readString("type");
                System.out.println(type + " which number?");

                String number = terminalInteraction.readString("number");

                System.out.println("By ratings:");
                manageProduction.printByRating(Double.valueOf(number), type);
                break;

            case "3":
                System.out.println("All:");
                System.out.println(productions);
                break;

            default:
                throw new RuntimeException("action");
        }
    }
}
