package production;

import production.details.Actor;
import production.details.ActorsDTO;
import production.details.Genre;
import production.details.Rating;

import java.util.ArrayList;
import java.util.List;

public class ProductionDTO {
    public String title;
    public String type;
    public List<String> actors;
    public List<String> directors;
    public List<Genre> genres;
    public List<RatingDTO> ratings;
    public String subject;
    public Double grade;
    public String plot;
    public Double averageRating;
    public String duration;
    public Integer releaseYear;
    public Integer numSeasons;
//    public sezoanele;

    public Production toProduction() {
        Production production;

        if (type.equals("Movie")) {
            production = new Movie();
        }
        else {
            production = new Series();
        }

        production.setGrade(grade);
        production.setType(type);
        production.setTitle(title);
        production.setActorsNames(actors);
        production.setDirectorsName(directors);
        production.setGenres(genres);

        List<Rating> ratings = new ArrayList<>();
        for (RatingDTO rd : this.ratings) {
            ratings.add(rd.toRating());
        }

        production.setRatings(ratings);
        production.setPlot(plot);
        production.setAverageRating(averageRating);
        production.setDuration(duration);
        production.setReleaseYear(releaseYear);

        return production;
    }

    public static class RatingDTO {
        public String username;
        public Integer grade;
        public String comment;

        public Rating toRating() {
            Rating rating = new Rating();
            rating.setUsername(username);
            rating.setGrade(grade);
            rating.setComment(comment);

            return rating;
        }
    }
}


