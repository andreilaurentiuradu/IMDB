package services;

import exceptions.InvalidCommandException;
import interaction.MenuBoard;
import javafx.util.Pair;
import production.Movie;
import production.Production;
import production.Series;
import production.details.Actor;
import production.details.Genre;
import production.details.Rating;
import repository.ActorRepository;
import repository.ProductionRepository;
import user.AccountType;
import user.User;
import user.staff.Staff;

import static services.ActionsService.terminalInteraction;

public class GeneralService {
    ActorRepository actorRepository;
    ProductionRepository productionRepository;

    public GeneralService(ActorRepository actorRepository, ProductionRepository productionRepository) {
        this.actorRepository = actorRepository;
        this.productionRepository = productionRepository;
    }

    public void search() {
        String title = terminalInteraction.readString(
                "What title/name do you want to search for?",
                "title/name");

        System.out.println("Possible answers could be: ");

        Actor actor = actorRepository.searchByName(title);
        if (actor != null)
            actor.displayActorInfo();

        productionRepository.printByTitle(title);
    }

    public void viewNotifications(User currentUser) {
        System.out.println(currentUser.getNotifications());
    }

    public void viewActors() {
        String answer = terminalInteraction.readString(
                "Do you want to sort them by name? Yes/No",
                "the answer");

        actorRepository.printActorDetails(answer);
    }

    public void viewProductionDetails() {
        MenuBoard.showFilterOptions();

        int action = terminalInteraction.readInt("Choose filter options");

        filterProductions(action);
    }

    public void filterProductions(int action) {
        switch (action) {
            case 1:
                String genre = terminalInteraction.readString("What genre do you want to filter by?\n" +
                        "Action, Adventure, Comedy, Drama, Horror, SF, Fantasy,\n" +
                        "Romance, Mystery, Thriller, Crime, Biography, War, Cooking",
                        "genre");

                productionRepository.printByGenre(Genre.getGenreType(genre));
                break;
            case 2:
                String type = terminalInteraction.readString("Under/Over/Equal?", "type");
                int number = terminalInteraction.readInt(type + " which number?");

                productionRepository.printByNumberOfRatings(number, type);
                break;
            case 3:
                String ratingType = terminalInteraction.readString("Under/Over/Equal?", "type");
                String ratingGrade = terminalInteraction.readString(ratingType + " which number?", "ratingGrade");

                productionRepository.printByAverageRating(Double.valueOf(ratingGrade), ratingType);
                break;
            case 4:
                productionRepository.printAll();
                break;
            default:
                throw new InvalidCommandException("Invalid filter option");
        }
    }

    public void addOrRemoveMediaIndustryFromSystem(User currentUser) {
        String action;
        action = terminalInteraction.readString("Add/Remove", "action");

        if (action.equals("Remove")) {
            removeMediaIndustryFromSystem(currentUser);

        } else if (action.equals("Add")) {
            addMediaIndustryToSystem();
        } else {
            throw new InvalidCommandException("Invalid operation");
        }
    }

    private void removeMediaIndustryFromSystem(User currentUser) {
        if (currentUser.getAccountType() == AccountType.CONTRIBUTOR) {
            Staff staff = (Staff) currentUser;
            System.out.println("Remove one from the list:");
            System.out.println(staff.getContributions());
            String value;
            value = terminalInteraction.readString("Introduce title/name for removing", "type");

            if (staff.isContribution(value)) {
                productionRepository.removeProduction(value);
                actorRepository.removeActor(value);
            } else {
                System.out.println("You can't remove this production");
                throw new RuntimeException("Invalid input");
            }

        } else {
            String value;
            value = terminalInteraction.readString("Introduce title/name for removing", "type");
            productionRepository.removeProduction(value);
            actorRepository.removeActor(value);
        }
    }

    private void addMediaIndustryToSystem() {
        String type;
        type = terminalInteraction.readString("Actor/Movie/Series", "type");
        String value;
        value = terminalInteraction.readString("Introduce title/name for adding", "type");

        switch (type) {
            case "Movie":
                productionRepository.addProduction(new Movie(value));
                break;
            case "Series":
                productionRepository.addProduction(new Series(value));
                break;
            case "Actor":
                actorRepository.addActor(new Actor(value));
                break;
            default:
                throw new RuntimeException("Invalid input");
        }
    }

    public void updateProductionOrActor(User currentUser) {
        String type = terminalInteraction.readString("Production/Actor", "type");
        if (type.equals("Production")) {
            updateProduction(currentUser);
        } else if (type.equals("Actor")) {
            updateJustActor(currentUser);
        } else {
            throw new InvalidCommandException("Invalid operation");
        }
    }

    public void updateJustActor(User currentUser) {
        String name = terminalInteraction.readString("Which actor do you want to update?", "name");
        System.out.println("Update one from the list:");
        Staff staff = (Staff) currentUser;
        System.out.println(staff.getContributions());
        updateJustActorAsContributor(name, staff);
    }

    private void updateJustActorAsContributor(String name, Staff staff) {
        if (staff.isContribution(name)) {
            Actor actor = actorRepository.searchByName(name);
            System.out.println("Which field do you want to update? ");
            String whichField = terminalInteraction.readString("Choose one from: Performances/Biography", "field");
            String updateField = terminalInteraction.readString("What is the new/for adding value?", "field");
            switch (whichField) {
                case "Performances":
                    Production production = productionRepository.searchByTitle(updateField);
                    Pair<String, Production> performance = new Pair<>(production.getType(), production);
//                    actor.addPerformances(performance);
//                    TODO fix
                    break;
                case "Biography":
                    actor.addToBiography(updateField);
                    break;
                default:
                    throw new RuntimeException("Field unrecognised");
            }
        } else {
            System.out.println("You can't update this production");
            throw new RuntimeException("Invalid production");
        }
    }

    public void updateProduction(User currentUser) {
        String title = terminalInteraction.readString("Which title do you want to update?", "title");
        System.out.println("Update one from the list:");
        Staff staff = (Staff) currentUser;
        System.out.println(staff.getContributions());
        updateJustProductionAsContributor(title, staff);
    }

    private void updateJustProductionAsContributor(String title, Staff staff) {
        if (staff.isContribution(title)) {
            Production production = productionRepository.searchByTitle(title);
            System.out.println("Which field do you want to update? ");
            String whichField = terminalInteraction.readString("Choose one from: Duration/ReleaseYear", "field");
            String updateField = terminalInteraction.readString("What is the new value?", "field");
            switch (whichField) {
                case "Duration":
//                    production.setDuration(updateField);
                    break;
                case "ReleaseYear":
                    production.setReleaseYear(Integer.parseInt(updateField));
                    break;
                default:
                    throw new RuntimeException("Field unrecognised");
            }
        } else {
            System.out.println("You can't update this production");
            throw new RuntimeException("Invalid production");
        }
    }

    public void addProductionRating(User user) {
        Rating rating = new Rating();
        rating.setUsername(user.getUsername());

        String title = terminalInteraction.readString("Which title do you want to rate", "title");
        String grade = terminalInteraction.readString("Rate this production from 1 to 10", "grade");

        if (Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 10) {
            throw new RuntimeException("Invalid rating");
        }

        String comment = terminalInteraction.readString("Add a comment", "comment");
        rating.setRating(Integer.parseInt(grade));
        rating.setComment(comment);
        productionRepository.addRating(title, user.getUsername(), rating);
    }
}
