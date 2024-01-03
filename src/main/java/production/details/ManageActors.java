package production.details;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManageActors {
    public List<Actor> actors = new ArrayList<>();

    public void printActorDetails(String wantedSorted) {
        if (wantedSorted.equals("Yes")) {
            actors.sort(new Comparator<Actor>() {
                @Override
                public int compare(Actor actor1, Actor actor2) {
                    return actor1.getName().compareTo(actor2.getName());
                }
            });
        }
        actors.forEach(System.out::println);
    }
}