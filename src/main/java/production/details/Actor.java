package production.details;

import javafx.util.Pair;
import production.MediaIndustry;

import java.util.ArrayList;
import java.util.List;

public class Actor extends MediaIndustry {
    private final String name;
    private final List<Pair<String, String>> performances = new ArrayList<>();
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

    public List<Pair<String, String>> getPerformances() {
        return performances;
    }


    public String getBiography() {
        return biography;
    }

    public void addPerformances(String key, String value) {
        performances.add(new Pair<>(key, value));
    }

    public void addPerformances(Pair<String, String> pair) {
        performances.add(pair);
    }

    public void updateBiography(String biography) {
        if (this.biography != null) {
            this.biography += biography;
        } else {
            this.biography = biography;
        }
    }

    public void displayActorInfo() {
        System.out.println(name);

        if (biography != null)
            System.out.println("\tBiography: " + biography);

        if (!performances.isEmpty()) {
            System.out.println("\tPerformances ");
            for (Pair<String, String> pair : performances) {
                System.out.println("\t\t" + pair.getValue() + " " + pair.getKey());
            }
        }
    }
}
