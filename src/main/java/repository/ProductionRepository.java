package repository;

import exceptions.InvalidCommandException;
import production.Production;
import production.details.Genre;
import production.details.Rating;

import java.util.List;
import java.util.Objects;

public class ProductionRepository {
    private final List<Production> productions;

    public ProductionRepository(List<Production> productions) {
        this.productions = productions;
    }

    public void printAll() {
        for (Production p : productions) {
            p.displayInfo();
        }
    }

    public void printByGenre(Genre genre) {
        for (Production p : productions) {
            if (p.getGenres().contains(genre)) {
                p.displayInfo();
            }
        }
    }

    public void printByNumberOfRatings(int number, String type) {
        switch (type) {
            case "Over":
                for (Production p : productions) {
                    if (p.getRatings().size() > number) {
                        p.displayInfo();
                    }
                }
                break;
            case "Equal":
                for (Production p : productions) {
                    if (p.getRatings().size() == number) {
                        p.displayInfo();
                    }
                }
                break;
            case "Under":
                for (Production p : productions) {
                    if (p.getRatings().size() < number) {
                        p.displayInfo();
                    }
                }
                break;
            default:
                throw new InvalidCommandException("Invalid filter type");
        }
    }

    public void printByAverageRating(Double number, String type) {
        switch (type) {
            case "Over":
                for (Production p : productions) {
                    if (p.getAverageRating() > number) {
                        p.displayInfo();
                    }
                }
                break;
            case "Equal":
                for (Production p : productions) {
                    if (Objects.equals(p.getAverageRating(), number)) {
                        p.displayInfo();
                    }
                }
                break;
            case "Under":
                for (Production p : productions) {
                    if (p.getRatings().size() < number) {
                        p.displayInfo();
                    }
                }
            default:
                throw new InvalidCommandException("Invalid filter type");
        }
    }

    public void printByTitle(String title) {
        for (Production p : productions) {
            if (p.getTitle().contains(title)) {
                p.displayInfo();
            }
        }
    }

    public Production searchByTitle(String title) {
        for (Production p : productions) {
            if (p.getTitle().equals(title))
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
