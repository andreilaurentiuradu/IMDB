package production;

import production.details.Episode;
import production.details.Genre;
import production.details.Rating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionDTO {
    public String title;
    public String type;
    public List<String> actors;
    public List<String> directors;
    public List<String> genres;
    public List<RatingDTO> ratings;
    public String plot;
    public String duration;
    public Double averageRating;
    public Integer releaseYear;
    public Integer numSeasons;
    public Map<String, List<EpisodeDTO>> seasons;


    public Production toProduction() {
        Production production;

        if (type.equals("Movie")) {
            production = new Movie(title);
        }
        else {
            production = new Series(title);
        }

        production.setType(type);
        production.setActorsNames(actors);
        production.setDirectorsName(directors);

        List<Genre> genres = new ArrayList<>();
        for (String g : this.genres) {
            genres.add(Genre.valueOf(g));
        }
        production.setGenres(genres);

        List<Rating> ratingsList = new ArrayList<>();
        for (RatingDTO rd : this.ratings) {
            ratingsList.add(rd.toRating());
        }

        production.setRatings(ratingsList);
        production.setPlot(plot);
        production.setDuration(duration);
        production.setAverageRating(averageRating);
        production.setReleaseYear(releaseYear);
        if (type.equals("Series")) {
            production.setNumSeasons(numSeasons);
            Map<String, List<Episode>> seasons = new HashMap<>();

            for (Map.Entry<String, List<EpisodeDTO>> entry : this.seasons.entrySet()) {
                List<Episode> episodes = new ArrayList<>();
                for (EpisodeDTO ed : entry.getValue()) {
                    episodes.add(ed.toEpisode());
                }
                seasons.put(entry.getKey(), episodes);
            }

            production.setSeasons(seasons);
        }

        return production;
    }

    public static class RatingDTO {
        public String username;
        public Integer rating;
        public String comment;

        public Rating toRating() {
            Rating rating = new Rating();
            rating.setUsername(username);
            rating.setRating(this.rating);
            rating.setComment(comment);

            return rating;
        }
    }

    public static class EpisodeDTO {
        public String episodeName;
        public String duration;

        public Episode toEpisode() {
            Episode episode = new Episode();
            episode.setName(episodeName);
            episode.setDuration(duration);
            return episode;
        }
    }
}


