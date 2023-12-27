import helper.JsonParser;
import production.MediaIndustry;
import production.Production;
import request.Request;
import production.details.Actor;
import user.User;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

public class IMDB {
    List<User> users;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

    public void run() {
        // incarcarea datelor in fielduri

        requests = JsonParser.parseRequest(getFile("requests.json"));

        requests.forEach(System.out::println);

        actors = JsonParser.parseActors(getFile("actors.json"));
        actors.forEach(System.out::println);

        productions = JsonParser.parseProduction(getFile("production.json"));
        productions.forEach(System.out::println);

        users = JsonParser.parseAccounts(getFile("accounts.json"));

        for (User user : users) {
            user.createFavorites(actors, productions);
        }

        users.forEach(System.out::println);
        // autentificarea utilizatorului
        // flowul aplicatiei in functie de rolul utilizatorului
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
