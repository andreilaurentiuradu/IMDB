package production;

import production.details.Genre;

import java.util.ArrayList;
import java.util.List;

public class ManageProduction {
    public List<Production> productions = new ArrayList<>();

    public void printByGenre(Genre genre) {
        for (Production p : productions) {
            if(p.getGenres().contains(genre)) {
                System.out.println(p);
            }
        }
    }

    public void printByRating(Double number, String type) {
        if (type.equals("Over")) {
            for (Production p : productions) {
                if (p.getAverageRating() > number) {
                    System.out.println(p);
                }
            }
        } else {
            if (type.equals("Under"))  {
                for (Production p : productions) {
                    if (p.getAverageRating() < number) {
                        System.out.println(p);
                    }
                }
            } else {
                for (Production p : productions) {
                    if (p.getAverageRating().equals(number)) {
                        System.out.println(p);
                    }
                }
            }
        }
    }
}
