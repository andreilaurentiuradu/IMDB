package user;

import request.Request;
import request.RequestType;
import request.RequestsManager;
import production.Production;
import production.details.Rating;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Regular extends User implements RequestsManager {

    @Override
    public Request createRequest(RequestType type, String description, String username, String value) {

        Request request = new Request(type, description, username);
        if (type == RequestType.MOVIE_ISSUE) {
            request.setProductionName(value);

        } else if (type == RequestType.ACTOR_ISSUE) {
            request.setActorName(value);
        }

        return request;
    }

    @Override
    public void removeRequest(Request r) {
        r.canceled = true;
    }

    @Override
    public String toString() {
        return "username=" + getUsername() +
                "\n Created requests=" + getCreatedRequests();
    }
}
