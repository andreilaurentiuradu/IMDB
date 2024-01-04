import helper.JsonParser;
import production.ManageProduction;
import production.MediaIndustry;
import production.Production;
import production.details.Genre;
import production.details.ManageActors;
import request.Request;
import production.details.Actor;
import request.RequestType;
import user.Credentials;
import user.ManageUsers;
import user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;

public class IMDB {
    private static IMDB instance;
    List<User> users;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

    private IMDB() {}

    public IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {
        // incarcarea datelor in fielduri

        requests = JsonParser.parseRequest(getFile("requests.json"));

//        requests.forEach(System.out::println);

        actors = JsonParser.parseActors(getFile("actors.json"));
//        actors.forEach(System.out::println);

        productions = JsonParser.parseProduction(getFile("production.json"));
//        productions.forEach(System.out::println);

        users = JsonParser.parseAccounts(getFile("accounts.json"));

        for (User user : users) {
            user.createFavorites(actors, productions);
        }

//        users.forEach(System.out::println);
        // autentificarea utilizatorului
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ManageUsers manageUsers = new ManageUsers();
        Credentials credentials = new Credentials();
        User currentUser;
        manageUsers.users = users;

        // citim credentialele
        do {
            System.out.println("Welcome back! Enter your credentials!");
            System.out.print("email: ");
            String email, password;

            try {
                email = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("can't read email from terminal", e);
            }

            System.out.print("password: ");

            try {
                password = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("can't read password from terminal", e);
            }

            credentials.setEmail(email);
            credentials.setPassword(password);
            currentUser = manageUsers.findUserByCredentials(credentials);

        }while(currentUser == null);

        // datele utilizatorului
        System.out.println("Welcome back user" + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());
        System.out.println(currentUser.getAccountType());

        // actiuni generale(nu depind de tipul de user)
        System.out.println("Choose action by pressing the correspondent number:");
        System.out.println("1) View productions details");
        System.out.println("2) View actors details");
        System.out.println("3) View notifications");
        System.out.println("4) Search for Actor/Movie/Series");
        System.out.println("5) Remove/Add Production/Actor from/to favorites List");
        String action;
        ManageActors manageActors = new ManageActors();
        ManageProduction manageProduction = new ManageProduction();
        manageProduction.productions = productions;
        manageActors.actors = actors;
        IMDB imdb = getInstance();

        Request request = new Request();
        // flowul aplicatiei in functie de rolul utilizatorului
        switch (currentUser.getAccountType()) {
            case REGULAR:
                System.out.println("6) Create/Discard a request");
                System.out.println("7) Add/Delete review");
                try{
                    action = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("can't read the action from terminal", e);
                }

                System.out.println("action: " + action);
                if(Integer.parseInt(action) < 6){
                    imdb.generalActions(action, manageActors, manageProduction, currentUser);
                } else {
                    switch (action) {
                        case "6":
                            System.out.println("Create/Discard?");

                            try{
                                action = reader.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException("can't read the action from terminal", e);
                            }

                            if (action.equals("Create")) {
                                System.out.println("What kind of request?");
                                Request r = new Request();
                                try{
                                    r.setType(RequestType.valueOf(reader.readLine()));
                                } catch (IOException e) {
                                    throw new RuntimeException("can't read the type from terminal", e);
                                }

                                System.out.println("Please describe the issue!");
                                try{
                                    r.setDescription(reader.readLine());
                                } catch (IOException e) {
                                    throw new RuntimeException("can't read the description from terminal", e);
                                }
                                r.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                                r.setRequesterUsername(currentUser.getUsername());
                                requests.forEach(System.out::println);
                                System.out.println("After");
                                requests.add(r);
                                requests.forEach(System.out::println);
                            }
                            break;
                        case "7":
                            break;
                        default:
                            throw new RuntimeException("Action not found");
                    }
                }


                break;
            case CONTRIBUTOR:
                System.out.println("6) Create/Discard a request");

                try{
                    action = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("can't read the action from terminal", e);
                }

                System.out.println("action: " + action);
                if(Integer.parseInt(action) < 6){
                    imdb.generalActions(action, manageActors, manageProduction, currentUser);
                } else {
                    switch (action) {
                        case "6":
                            System.out.println("Create/Discard?");

                            try{
                                action = reader.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException("can't read the action from terminal", e);
                            }

                            if (action.equals("Create")) {
                                System.out.println("What kind of request?");
                                Request r = new Request();
                                try{
                                    r.setType(RequestType.valueOf(reader.readLine()));
                                } catch (IOException e) {
                                    throw new RuntimeException("can't read the type from terminal", e);
                                }

                                System.out.println("Please describe the issue!");
                                try{
                                    r.setDescription(reader.readLine());
                                } catch (IOException e) {
                                    throw new RuntimeException("can't read the description from terminal", e);
                                }
                                r.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                                r.setRequesterUsername(currentUser.getUsername());
                                requests.forEach(System.out::println);
                                System.out.println("After");
                                requests.add(r);
                                requests.forEach(System.out::println);
                            }
                            break;
                        case "7":
                            break;
                        case "8":
                            break;
                        default:
                            throw new RuntimeException("Action not found");
                    }
                }
                break;
            case ADMIN:
                try{
                    action = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("can't read the action from terminal", e);
                }

                System.out.println("action: " + action);
                imdb.generalActions(action, manageActors, manageProduction, currentUser);
                break;
            default:
                throw new RuntimeException("AccountType not specified");
        }
    }

    public void generalActions(String action, ManageActors manageActors, ManageProduction manageProduction,
                               User currentUser) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        switch (action) {
            case "1":
                System.out.println("Filter results by:");
                System.out.println("1) Genre");
                System.out.println("2) Ratings");
                System.out.println("3) Don't filter");

                try{
                    action = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("can't read the action from terminal", e);
                }

                switch (action) {
                    case "1":
                        System.out.println("What genre do you want?");
                        String genre;

                        try{
                            genre = reader.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException("can't read the genre from terminal", e);
                        }

                        manageProduction.printByGenre(Genre.valueOf(genre));
                        break;

                    case "2":
                        System.out.println("Under/Over/Equal?");
                        String type;
                        try{
                            type = reader.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException("can't read the tyoe from terminal", e);
                        }
                        System.out.println(type + " which number?");

                        String number;
                        try{
                            number = reader.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException("can't read the number from terminal", e);
                        }

                        System.out.println("By ratings:");
                        manageProduction.printByRating(Double.valueOf(number), type);
                        break;

                    case "3":
                        System.out.println("All:");
                        System.out.println(productions);
                        break;

                    default:
                        throw new RuntimeException("can't read the action from terminal");
                }
                break;
            case "2":
                System.out.println("Do you want to sort them by name? Yes/No");
                String answer;
                try{
                    answer = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Can't read the answer from terminal", e);
                }

                manageActors.printActorDetails(answer);
                break;
            case "3":
                System.out.println(currentUser.getNotifications());
                break;
            case "4":
                System.out.println("What title/name do you want to search for?");
                String title;
                try{
                    title = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Can't read the title/name from terminal", e);
                }

                System.out.println("Possible answers could be: ");
                manageActors.searchByName(title);
                manageProduction.searchByTitle(title);
                break;
            case "5":
                System.out.println("Remove/Add");
                try{
                    action = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Can't read the action from terminal", e);
                }

                System.out.println("What actor/production?");
                try{
                    title = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException("Can't read the name/title from terminal", e);
                }

                if (action.equals("Add")) {
                    currentUser.addMediaIndustry(new MediaIndustry(title));
                } else {
                    if (action.equals("Remove")) {
                        currentUser.removeMediaIndustry(new MediaIndustry(title));
                    } else {
                        throw new RuntimeException("Action not found");
                    }
                }
                System.out.println("The new favorite list is:");
                System.out.println(currentUser.getFavorites());
                break;

            default:
                throw new RuntimeException("This action doesn't exist");
        }
    }

    private File getFile(String filename) {
        return new File(
                this.getClass().getClassLoader().getResource(filename).getFile()
        );
    }

    public static void main(String[] args) {
        new IMDB().run();
    }

}
