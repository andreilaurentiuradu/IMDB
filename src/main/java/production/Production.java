package production;

import production.details.Actor;
import production.details.Episode;
import production.details.Genre;
import production.details.Rating;

import java.lang.reflect.Field;
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
        StringBuilder sb = new StringBuilder("Production{");

        if (title != null)
            sb.append("title='").append(title).append('\'').append(", ");
        if (type != null)
            sb.append("type='").append(type).append('\'').append(", ");
        if (actorsNames != null)
            sb.append("actorsNames=").append(actorsNames).append(", ");
        if (directorsName != null)
            sb.append("directorsName=").append(directorsName).append(", ");
        if (genres != null)
            sb.append("genres=").append(genres).append(", ");
        if (ratings != null)
            sb.append("ratings=").append(ratings).append(", ");
        if (plot != null)
            sb.append("plot='").append(plot).append('\'').append(", ");
        if (duration != null)
            sb.append("duration='").append(duration).append('\'').append(", ");
        if (averageRating != null)
            sb.append("averageRating=").append(averageRating).append(", ");
        if (releaseYear != null)
            sb.append("releaseYear=").append(releaseYear).append(", ");
        if (numSeasons != null)
            sb.append("numSeasons=").append(numSeasons).append(", ");
        if (seasons != null)
            sb.append("seasons=").append(seasons).append(", ");

        if (sb.length() > 12) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append('}');

        return sb.toString();
    }
}

