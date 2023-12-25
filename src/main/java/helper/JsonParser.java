package helper;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import production.Production;
import production.ProductionDTO;
import production.details.Actor;
import production.details.ActorsDTO;
import request.Request;
import request.RequestDTO;

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
//            return mapper.readValue(filename, new TypeReference<List<Request>>() {});
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
            List<ActorsDTO> actorsVos = mapper.readValue(file, new TypeReference<List<ActorsDTO>>() {});
            List<Actor> actors = new ArrayList<>();

            for (ActorsDTO av: actorsVos) {
                actors.add(av.toActor());
            }

            return actors;
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
    public static List<Production> parseProduction(File file) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<ProductionDTO> productionDTOs = objectMapper.readValue(file, new TypeReference<List<ProductionDTO>>() {});
            List<Production> productions = new ArrayList<>();
            for(ProductionDTO pd : productionDTOs) {
                productions.add(pd.toProduction());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to read from file", e);
        }
        return null;
    }
}
