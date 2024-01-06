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
    public Request createRequest(RequestType type, String description, LocalDateTime currentDate, String username, String value) {

        return new Request(type, description, currentDate, username);
    }

    @Override
    public void removeRequest(Request r) {

    }

    public void addReview (Production production, Rating rating) {

        production.getRatings().add(rating);
    }
}
