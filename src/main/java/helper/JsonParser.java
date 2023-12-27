package helper;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import production.Production;
import production.ProductionDTO;
import production.details.Actor;
import production.details.ActorsDTO;
import request.Request;
import request.RequestDTO;
import user.AccountType;
import user.User;
import user.UserDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;

public class JsonParser {
    public static List<Request> parseRequest(File file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<RequestDTO> requestVos = mapper.readValue(file, new TypeReference<List<RequestDTO>>() {});
            List<Request> requests = new ArrayList<>();
            for (RequestDTO rv : requestVos) {
                requests.add(rv.toRequest());
            }
            return requests;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " , e);

        } catch (IOException e) {
            throw new RuntimeException("Unable to read from ", e);
        }
        return null;
    }

    public static List<Actor> parseActors(File file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ActorsDTO> actorsDTOs = mapper.readValue(file, new TypeReference<List<ActorsDTO>>() {});
            List<Actor> actors = new ArrayList<>();

            for (ActorsDTO av: actorsDTOs) {
                actors.add(av.toActor());
            }

            return actors;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("File cannot be parsed: " , e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " , e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from ", e);
        }
    }
    public static List<Production> parseProduction(File file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ProductionDTO> productionDTOs = mapper.readValue(file, new TypeReference<List<ProductionDTO>>() {});
            List<Production> productions = new ArrayList<>();
            for(ProductionDTO pd : productionDTOs) {
                productions.add(pd.toProduction());
            }
            return productions;
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to parse", e);
        }
    }

    public static List<User> parseAccounts(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<UserDTO> userDTOS = mapper.readValue(file, new TypeReference<List<UserDTO>>() {});
            List<User> users = new ArrayList<>();
            for (UserDTO ud : userDTOS) {
                users.add(ud.toUser());
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from ", e);
        }
    }
}
