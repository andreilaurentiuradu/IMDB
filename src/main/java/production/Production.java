package production;

import production.details.Actor;
import production.details.Episode;
import production.details.Genre;
import production.details.Rating;

import java.util.List;
import java.util.Map;

public abstract class Production extends MediaIndustry implements Comparable<Production> {
    String title;
    private String type;
    private List<String> actorsNames;
    private List<String> directorsName;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private String duration;
    private Double averageRating;
    private Integer releaseYear;
    private Integer numSeasons;
    private Map<String, List<Episode>> seasons;

    public Production(String title) {
        super(title);
        this.title = title;
    }

    public static Production getProductionByTitle(List<Production> productions, String title) {
        for (Production p : productions) {
            if (p.title.equals(title)) {
                return p;
            }
        }

        return null;
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
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

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setNumSeasons(Integer numSeasons) {
        this.numSeasons = numSeasons;
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

    public void setDirectorsName(List<String> directorsName) {
        this.directorsName = directorsName;
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

    @Override
    public String toString() {
        return "Production{" +
                "title='" + title + '\'' +
//                ", type='" + type + '\'' +
//                ", actorsNames=" + actorsNames +
//                ", directorsName=" + directorsName +
//                ", genres=" + genres +
//                ", ratings=" + ratings +
//                ", plot='" + plot + '\'' +
//                ", duration='" + duration + '\'' +
//                ", averageRating=" + averageRating +
//                ", releaseYear=" + releaseYear +
//                ", numSeasons=" + numSeasons +
//                ", seasons=" + seasons +
                '}';
    }
}

