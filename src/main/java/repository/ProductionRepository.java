package repository;

import exceptions.InvalidCommandException;
import production.Production;
import production.details.Genre;
import production.details.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductionRepository {
    private final List<Production> productions;

    public ProductionRepository(List<Production> productions) {
        this.productions = productions;
    }

    public List<Production> getProductionsRatedByUser(String username) {
        List<Production> ratedProductions = new ArrayList<>();

        for (Production p : productions) {
            for (Rating r : p.getRatings()) {
                if (r.getUsername().equals(username)) {
                    ratedProductions.add(p);
                    System.out.println(p.getTitle() + "\n" + r);
                    break;
                }
            }
        }

        System.out.println();
        return ratedProductions;
    }

    public void viewUnratedProductionsTitles(String username) {
        System.out.println();
        for (Production p : productions) {
            if (!p.isRatedBy(username))
                System.out.println(p.getTitle());
        }
        System.out.println();
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
                break;
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

    public void addRatingAndShowProductionRatings(Production production, Rating rating) {
        production.getRatings().add(rating);

        System.out.println(production.getTitle() + " ratings:");
        for (Rating r : production.getRatings()) {
            System.out.println(r);
        }
    }

    public boolean isAlreadyRatedByUser(List<Rating> ratings, String username) {
//        only one review for a production per user
        for (Rating r : ratings) {
            if (r.getUsername().equals(username)) {
                System.out.println("You already rated this production:");
                System.out.println("\t" + r);
                return true;
            }
        }
        return false;
    }
}
