package user;

import request.Request;
import request.RequestType;
import request.RequestsManager;
import production.Production;
import production.details.Rating;

import java.time.LocalDateTime;
import java.util.Locale;

public class Regular extends User implements RequestsManager {

    public Regular() {

    }
    public Regular(User user) {
        super(user.getInformation(), user.getAccountType(), user.getUsername(), user.getExperience(), user.getNotifications(), user.getFavorites());
    }
    @Override
    public Request createRequest(String type, String description, LocalDateTime currentDate, String username) {
        RequestType requestType = RequestType.valueOf(type);

        return new Request(requestType, description, currentDate, username);
    }

    @Override
    public void removeRequest(Request r) {

    }

    public void addReview (Production production, Rating rating) {

        production.getRatings().add(rating);
    }


//    Adăugarea unei recenzii (element de tip Rating) pentru o product, ie (va nota cu puncte
//    de la 1 la 10 s, i va adăuga un comentariu).
}
