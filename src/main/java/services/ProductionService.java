package services;

import exceptions.InvalidCommandException;
import production.Movie;
import production.Production;
import production.Series;
import production.details.Actor;
import production.details.Rating;
import repository.ActorRepository;
import repository.ProductionRepository;
import request.Request;
import user.User;
import user.staff.Admin;
import user.staff.Staff;

import java.util.List;

import static repository.UserRepository.SUPREME;
import static services.ActionsService.terminalInteraction;

public class ProductionService {
    private final ProductionRepository productionRepository;
    private final ActorRepository actorRepository;

    public ProductionService(ProductionRepository productionRepository, ActorRepository actorRepository) {
        this.productionRepository = productionRepository;
        this.actorRepository = actorRepository;
    }

    public void updateProduction(Staff currentUser) {
        currentUser.viewMediaIndustryUserCanUpdate();

        String type = terminalInteraction.readString("Movie/Series?");
        String title = terminalInteraction.readString("Which title do you want to update?");

        if (!currentUser.isAllowedToUpdate(title)) {
            System.out.println("You are not allowed to update this resource");
            return;
        }

        String whichField = terminalInteraction.readString("Choose one from: Plot/Release year");
        String value = terminalInteraction.readString("What should we add to old value?(Plot)/What is the new value?(Release year)");

        Production update;

        if (type.equals("Movie")) {
            update = new Movie(title);
        } else {
            update = new Series(title);
        }

        updateProductionFields(currentUser, whichField, value, update);
    }

    public static void updateMovie(Request request, Staff currentUser) {
        String whichField = terminalInteraction.readString("Choose one from: Plot/Release year");
        String value = terminalInteraction.readString("What is the new value?");

        Production update = new Movie(request.getProductionName());

        updateProductionFields(currentUser, whichField, value, update);
    }

    private static void updateProductionFields(Staff currentUser, String whichField, String value, Production update) {
        switch (whichField) {
            case "Plot":
                update.setPlot(value);
                break;
            case "Release year":
                update.setReleaseYear(Integer.parseInt(value));
                break;
            default:
                System.out.println("Invalid field to update");
        }

        currentUser.updateProduction(update);
    }

    public void addOrRemoveProductionRating(User currentUser) {
        String option = terminalInteraction.readString("Add/Remove");

        if (option.equals("Add")) {
            addProductionRating(currentUser);
        } else if (option.equals("Remove")) {
            removeProductionRating(currentUser);
        } else {
            System.out.println("Invalid command");
        }
    }

    private void removeProductionRating(User currentUser) {
        List<Production> ratedProductions = productionRepository.getProductionsRatedByUser(currentUser.getUsername());

        if (ratedProductions.isEmpty()) {
            System.out.println("No rating available");
        }

        String title = terminalInteraction.readString("Choose production title you want to remove the rating");

        productionRepository.getProductionsRatedByUser(currentUser.getUsername()); // for debbug

        for (Production p : ratedProductions) {
            if (p.getTitle().equals(title)) {
                p.removeProductionRateFromUser(currentUser.getUsername());
            }
        }

        productionRepository.printAll();
    }

    private void addProductionRating(User user) {
        Rating rating = new Rating();
        rating.setUsername(user.getUsername());

        productionRepository.viewProductionsTitle();
        String title = terminalInteraction.readString("Which title do you want to rate", "title");

        Production production = productionRepository.searchByTitle(title);

        if (productionRepository.isAlreadyRatedByUser(production.getRatings(), user.getUsername()))
            return;

        String grade = terminalInteraction.readString("Rate this production from 1 to 10", "grade");

        if (Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 10) {
            System.out.println("Invalid rating. Please try again");
            return;
        }

        String comment = terminalInteraction.readString("Add a comment");
        rating.setRating(Integer.parseInt(grade));
        rating.setComment(comment);

        productionRepository.addRatingAndShowProductionRatings(production, rating);
    }

    public void addOrRemoveMediaIndustry(Staff currentUser) {
        String action;
        action = terminalInteraction.readString("Add/Remove", "action");

        if (action.equals("Remove")) {
            removeMediaIndustry(currentUser);
        } else if (action.equals("Add")) {
            addMediaIndustry(currentUser);
        } else {
            throw new InvalidCommandException("Invalid operation");
        }
    }

    private void removeMediaIndustry(Staff currentUser) {
        System.out.println("Remove one from your contributions list:");
        System.out.println(currentUser.getContributions());
        if (currentUser instanceof Admin) {
            System.out.println("Or from ADMINS contribution list:");
            System.out.println(SUPREME.getContributions());
        }
        String value = terminalInteraction.readString("Introduce title/name for removing");

        if (actorRepository.isActor(value)) {
            currentUser.removeActorSystem(value);
        } else {
            currentUser.removeProductionSystem(value);
        }
    }

    private void addMediaIndustry(Staff currentUser) {
        String type = terminalInteraction.readString("Actor/Movie/Series");
        String value;

        switch (type) {
            case "Movie":
                value = terminalInteraction.readString("Introduce movie title for adding");
                currentUser.addProductionSystem(new Movie(value));
                break;
            case "Series":
                value = terminalInteraction.readString("Introduce series title for adding");
                currentUser.addProductionSystem(new Series(value));
                break;
            case "Actor":
                value = terminalInteraction.readString("Introduce actor name for adding");
                currentUser.addActorSystem(new Actor(value));
                break;
            default:
                throw new RuntimeException("Invalid input");
        }
    }
}
