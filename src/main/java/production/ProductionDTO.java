package production;

import production.details.Genre;
import production.details.Rating;

import java.util.List;

public class ProductionDTO {
    public String title;
    public String type;
//    public List<String> actors;
//    public List<String> directors;
//    public List<Genre> genres;
//    public List<Rating> ratings;
//    public String subject;
//    public Double grade;

    public Production toProduction() {
        Production production = new Production() {
            @Override
            public void displayInfo() {

            }
        };

//        production.setGrade(grade);
        production.setType(type);
        production.setTitle(title);

        return production;
    }
}


