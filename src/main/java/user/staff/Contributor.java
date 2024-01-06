package user.staff;

import production.MediaIndustry;
import production.Production;
import production.details.Actor;
import request.Request;
import request.RequestType;
import request.RequestsManager;
import user.Credentials;
import user.User;
import user.staff.Staff;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Contributor extends Staff implements RequestsManager {
    public Contributor() {
    }

    public Contributor(User user) {
        super(user);
    }

    @Override
    public Request createRequest(RequestType type, String description, LocalDateTime currentDate, String username, String value) {
        Request request = new Request(type, description, currentDate, username);
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
