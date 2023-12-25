package production.details;

import javafx.util.Pair;
import production.Production;

import java.util.List;

public class Actor {
    private String name;
    private List<Pair<String, Production>> performances;
    private String biography;

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

    public String toString() {
       return name + " " + biography;
    }
}
