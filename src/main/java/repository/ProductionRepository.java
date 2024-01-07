package repository;

import production.Production;
import production.details.Genre;
import production.details.Rating;
import user.AccountType;
import user.Regular;
import user.User;
import user.staff.Contributor;
import user.staff.Staff;

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

    public void printByTitle(String title) {
        for (Production p : productions) {
            if (p.getTitle().contains(title)) {
                System.out.println(p);
            }
        }
    }

    public Production searchByTitle(String title) {
        for (Production p : productions) {
            if(p.getTitle().equals(title))
                return p;
        }
        return null;
    }

    public void addProduction(Production production) {
        productions.add(production);
    }

    public void removeProduction(String title) {
        productions.remove(searchByTitle(title));
    }

    public void addRating(String title, String username, Rating rating) {
        Production production = searchByTitle(title);
        for (Rating r : production.getRatings()) {
            if (r.getUsername().equals(username)) {
                return;
            }
        }
        production.getRatings().add(rating);
    }
}
