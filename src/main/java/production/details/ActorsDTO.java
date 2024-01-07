package production.details;

import javafx.util.Pair;
import production.Movie;
import production.Production;
import production.Series;

import java.util.ArrayList;
import java.util.List;

public class ActorsDTO {
    public String name;
    public List<PerformanceDTO> performances;
    public String biography;

    public Actor toActor() {
        Actor actor = new Actor(name);

        actor.setBiography(biography);

        List<Pair<String, String>> performances = new ArrayList<>();
        for (PerformanceDTO pv : this.performances) {
            actor.addPerformances(pv.title, pv.type);
        }


        return actor;
    }

    public static class PerformanceDTO {
        public String title;
        public String type;

        public Pair<String, Production> toPair() {
            Production production;

            if (type.equals("Movie")) {
                production = new Movie(title);
            } else {
                production = new Series(title);
            }
            return new Pair<>(title, production);
        }
    }
}
