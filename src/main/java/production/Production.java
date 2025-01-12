package production;

import production.details.Actor;
import production.details.Genre;
import production.details.Rating;
import user.AccountType;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static services.ActionsService.userRepository;

public abstract class Production extends MediaIndustry implements Comparable<Production> {
    final String title;
    private String type;
    private List<String> actorsNames;
    private List<String> directorsName;
    private List<Genre> genres;
    private List<Rating> ratings = new ArrayList<>();
    private String plot;
    private Double averageRating;
    private Integer releaseYear;

    public Production(String title) {
        super(title);
        this.title = title;
    }

    public boolean isRatedBy(String username) {
        for (Rating r : ratings) {
            if (r.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void removeProductionRateFromUser(User user) {
        Rating toDelete = null;

        for (Rating r : ratings) {
            if (r.getUsername().equals(user.getUsername())){
                toDelete = r;
                break;
            }
        }

        toDelete.removeObserver(user);
        ratings.remove(toDelete);
    }

    public static Production getProductionByTitle(List<Production> productions, String title) {
        for (Production p : productions) {
            if (p.title.equals(title)) {
                return p;
            }
        }

        return null;
    }


    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActorsNames(List<String> actorsNames) {
        this.actorsNames = actorsNames;
    }
    public List<String> getActorsNames() {
        return this.actorsNames;
    }

    public void setDirectorsName(List<String> directorsName) {
        this.directorsName = directorsName;
    }

    public String getPlot() {
        return plot;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public List<String> getDirectorsName() {
        return this.directorsName;
    }
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public abstract void displayInfo();

    public void updateAverageRating() {
        double sum = 0;
        for (Rating rating : getRatings()) {
            sum += rating.getValue();
        }
        setAverageRating(sum / getRatings().size());
    }
    void displayCommonInfo() {
        printIfNotNull("\tType:", getType());
        if (releaseYear != null) {
            System.out.println("\tReleaseYear:" + getReleaseYear());
        }

        if (getActorsNames() != null) {
            System.out.println("\tActor's name:");
            for (String name : getActorsNames()) {
                System.out.println("\t\t" + name);
            }
        }

        if (getDirectorsName() != null) {
            System.out.println("\tDirector's names:");
            for (String name : getDirectorsName()) {
                System.out.println("\t\t" + name);
            }
        }

        if (averageRating != null) {
            System.out.println("\tAverage rating:" + getAverageRating());
        }

        printIfNotNull("\tPlot:", getPlot());

        if (getRatings() != null && !getRatings().isEmpty()) {
            System.out.println("\tRatings:");
            displayReviewsByUserExperience();
        }
    }

    public void printIfNotNull(String message, String value) {
        if (value != null)
            System.out.println(message + " " + value);
    }

    public void addActor(Actor actor) {
        actorsNames.add(actor.getName());
    }

    private void displayReviewsByUserExperience() {
        List<Rating> ratings = getRatings();
        ratings.sort(new Comparator<Rating>() {
            @Override
            public int compare(Rating o1, Rating o2) {
                User user1 = userRepository.findUserByUsername(o1.getUsername());
                User user2 = userRepository.findUserByUsername(o2.getUsername());

                if (user1.getAccountType() == AccountType.ADMIN) {
                    if (user2.getAccountType() == AccountType.ADMIN) {
                        return user1.getUsername().compareTo(user2.getUsername());
                    }
                    return -1;
                }

                if (user2.getAccountType() == AccountType.ADMIN) {
                    return 1;
                }

                return user2.getExperience() - user1.getExperience();
            }
        });

        for (Rating rating : getRatings()) {
            System.out.println("\t\t" + rating);
        }
    }
}

