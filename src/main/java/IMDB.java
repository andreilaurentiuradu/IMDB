import helper.JsonParser;
import production.ManageProduction;
import production.MediaIndustry;
import production.Production;
import production.details.Genre;
import production.details.ManageActors;
import request.Request;
import production.details.Actor;
import user.Credentials;
import user.ManageUsers;
import user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class IMDB {
    List<User> users;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

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
        String action;

        try{
            action = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("can't read the action from terminal", e);
        }

        System.out.println("action: " + action);
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

                ManageProduction manageProduction = new ManageProduction();
                manageProduction.productions = productions;

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
                    throw new RuntimeException("can't read the answer from terminal", e);
                }

                ManageActors manageActors = new ManageActors();
                manageActors.actors = actors;
                manageActors.printActorDetails(answer);
                break;
            case "3":
                System.out.println(currentUser.getNotifications());
                break;
            default:
                throw new RuntimeException("this action doesn't exist");
        }

        // flowul aplicatiei in functie de rolul utilizatorului
        switch (currentUser.getAccountType()) {
            case REGULAR:
                break;
            case ADMIN:
                break;
            case CONTRIBUTOR:
                break;
            default:
                throw new RuntimeException("AccountType not specified");
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
