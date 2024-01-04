package production.details;

import javafx.util.Pair;
import production.MediaIndustry;
import production.Production;

import java.util.List;

public class Actor extends MediaIndustry {
    private String name;
    private List<Pair<String, Production>> performances;
    private String biography;

    public Actor (String name) {
        super(name);
        this.name = name;
    }

    public static Actor getActorByName(List<Actor> actors, String name) {
        for (Actor a : actors) {
            if (a.name.equals(name)) {
                return a;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pair<String, Production>> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Pair<String, Production>> performances) {
        this.performances = performances;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
//                ", performances=" + performances +
//                ", biography='" + biography + '\'' +
                '}';
    }
}
