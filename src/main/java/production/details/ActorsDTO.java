package production.details;

import java.util.List;

public class ActorsDTO {
    public String name;
    public List<PerformanceDTO> performances;
    public String biography;

    public Actor toActor() {
        Actor actor = new Actor(name);

        actor.updateBiography(biography);

        for (PerformanceDTO pv : this.performances) {
            actor.addPerformances(pv.title, pv.type);
        }

        return actor;
    }

    public static class PerformanceDTO {
        public String title;
        public String type;
    }
}
