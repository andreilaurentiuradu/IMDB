package production;

import production.details.Genre;
import production.details.Rating;

import java.util.List;

public abstract class Production implements Comparable<Production> {
    private String title;
    private String type;
    private List<String> actorsNames;
    private List<String> directorsName;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String subject;
    private Double grade;
    private String plot;
    private String duration;
    private Double averageRating;

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

    private Integer releaseYear;
    private Integer numSeasons;
//    private sezoanele;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public abstract void displayInfo();

    @Override
    public String toString() {
        return "Production{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", actorsNames=" + actorsNames +
                ", directorsName=" + directorsName +
                ", genres=" + genres +
                ", subject='" + subject + '\'' +
                ", grade=" + grade +
                ", plot='" + plot + '\'' +
                ", duration='" + duration + '\'' +
                ", averageRating=" + averageRating +
                ", releaseYear=" + releaseYear +
                '}';
    }

    public int compareTo (Production production) {
        return title.compareTo(production.title);
    }
}
