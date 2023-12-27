import helper.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import production.Production;
import production.ProductionDTO;
import request.Request;
import user.AccountType;
import production.details.Actor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IMDB {
    List<AccountType> accountTypes;
    List<Actor> actors;
    List<Request> requests;
    List<Production> productions;

    public void run() {
        // incarcarea datelor in fielduri
        File fileRequests= new File(
                this.getClass().getClassLoader().getResource("requests.json").getFile()
        );
        requests = JsonParser.parseRequest(fileRequests);

//        requests.forEach(System.out::println);


        File fileActors = new File(
                this.getClass().getClassLoader().getResource("actors.json").getFile()
        );

        actors = JsonParser.parseActors(fileActors);
//        actors.forEach(System.out::println);


        File fileProduction = new File(
                this.getClass().getClassLoader().getResource("production.json").getFile()
        );

        productions = JsonParser.parseProduction(fileProduction);
        productions.forEach(System.out::println);

        // autentificarea utilizatorului
        // flowul aplicatiei in functie de rolul utilizatorului
    }

    public static void main(String[] args) {
        new IMDB().run();
    }

}
