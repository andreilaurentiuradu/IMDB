import helper.JsonParser;
import production.MediaIndustry;
import production.Production;
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
        Credentials credentials;
        User user;
        manageUsers.users = users;

        // citim credentialele
        do {
            System.out.println("Please enter");
            System.out.print("Email: ");
            String email, password;
            credentials = new Credentials();
            try {
                email = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("can't read email from terminal", e);
            }

            System.out.print("Password: ");
            try {
                password = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("can't read password from terminal", e);
            }

            credentials.setEmail(email);
            credentials.setPassword(password);
            user = manageUsers.findUserByCredentials(credentials);
        }while(user == null);

        System.out.println("Welcome " + user.getUsername());
        System.out.println(user.getAccountType());

        // flowul aplicatiei in functie de rolul utilizatorului
        switch (user.getAccountType()) {
            case REGULAR:
                System.out.println("intra");
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
