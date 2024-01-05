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

    public final List<MediaIndustry> contributions = new ArrayList<>();

    public Contributor() {
    }

    public Contributor(User user) {
        super(user);
    }

    @Override
    public Request createRequest(String type, String description, LocalDateTime currentDate, String username) {
        RequestType requestType = RequestType.valueOf(type);

        return new Request(requestType, description, currentDate, username);
    }

    @Override
    public void removeRequest(Request r) {

    }


    public void addContributions(List<String> actorsName, List<String> productionTitles) {
        for (String s : actorsName) {
            contributions.add(new MediaIndustry(s));
        }

        for (String s : productionTitles) {
            contributions.add(new MediaIndustry(s));
        }
    }
}
