package user.staff;

import request.Request;
import request.RequestType;
import request.RequestsManager;
import user.User;
import user.staff.Staff;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Contributor extends Staff implements RequestsManager {

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
        requests.remove(r);
    }
}
