package repository;

import production.Production;
import production.details.Genre;

import java.util.List;

public class ProductionRepository {
    private final List<Production> productions;

    public ProductionRepository(List<Production> productions) {
        this.productions = productions;
    }

    public void printAll() {
        System.out.println("All:");

        for (Production p : productions) {
                System.out.println(p);
        }
    }

    public void printByGenre(Genre genre) {
        for (Production p : productions) {
            if (p.getGenres().contains(genre)) {
                System.out.println(p);
            }
        }
    }

    public void printByRating(Double number, String type) {
        System.out.println("By ratings:");

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

    public void searchByTitle(String title) {
        for (Production p : productions) {
            if (p.getTitle().contains(title)) {
                System.out.println(p);
            }
        }
    }

}
