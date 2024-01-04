package user;

import request.Request;
import request.RequestsManager;
import production.Production;
import production.details.Rating;

import java.time.LocalDateTime;
import java.util.Locale;

public class Regular extends User implements RequestsManager {
    @Override
    public void createRequest(Request r) {
        r.setCreationDate(LocalDateTime.now());
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
