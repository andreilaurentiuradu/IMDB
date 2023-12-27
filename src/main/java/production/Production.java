package production;

import production.details.Episode;
import production.details.Genre;
import production.details.Rating;

import java.util.List;
import java.util.Map;

public abstract class Production implements Comparable<Production> {
    private String title;
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

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getNumSeasons() {
        return numSeasons;
    }

    public void setNumSeasons(Integer numSeasons) {
        this.numSeasons = numSeasons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getActorsNames() {
        return actorsNames;
    }

    public void setActorsNames(List<String> actorsNames) {
        this.actorsNames = actorsNames;
    }

    public List<String> getDirectorsName() {
        return directorsName;
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
                ", numSeasons=" + numSeasons +
                ", seasons=" + seasons +
                '}';
    }

    public int compareTo (Production production) {
        return title.compareTo(production.title);
    }
}
//
//    Nota filmului, egală media aritmetică a tuturor evaluărilor primite de la utilizatori -
//        Double;
//        • public abstract void displayInfo();
//        Metodă pentru afis,area informat, iilor specifice fiecărei subclase
//        • public int compareTo(Object o);
//        Metodă necesară sortării filmelor s, i serialelor în funct, ie de titlu
