package helper;

import production.Production;
import production.ProductionDTO;
import production.details.Actor;
import production.details.ActorsDTO;
import request.Request;
import request.RequestDTO;
import user.User;
import user.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class LoadData {
    static JsonParse jsonParse = new JsonParse();

    public static List<Request> loadRequests() {
        List<RequestDTO> requestDTO = jsonParse.parseList("requests.json", RequestDTO.class);

        List<Request> requests = new ArrayList<>();
        for (RequestDTO rv : requestDTO) {
            requests.add(rv.toRequest());
        }

        return requests;
    }

    public static List<Actor> loadActors() {
        List<ActorsDTO> actorsDTOs = jsonParse.parseList("actors.json", ActorsDTO.class);

        List<Actor> actors = new ArrayList<>();
        for (ActorsDTO av: actorsDTOs) {
            actors.add(av.toActor());
        }

        return actors;
    }

    public static List<Production> loadProduction() {
        List<ProductionDTO> productionDTOs = jsonParse.parseList("production.json", ProductionDTO.class);

        List<Production> productions = new ArrayList<>();
        for(ProductionDTO pd : productionDTOs) {
            productions.add(pd.toProduction());
        }

        return productions;
    }

    public static List<User> loadUsers() {
        List<UserDTO> userDTOS = jsonParse.parseList("accounts.json", UserDTO.class);

        List<User> users = new ArrayList<>();
        for (UserDTO ud : userDTOS) {
            users.add(ud.toUser());
        }

        return users;
    }
}
