package services;

import exceptions.InvalidCommandException;
import interaction.MenuBoard;
import production.Production;
import production.details.Actor;
import production.details.Genre;
import repository.ActorRepository;
import repository.ProductionRepository;
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
                String genre = terminalInteraction.readString("What genre do you want to filter by?\n");
                Genre.printAll();

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

    private void updateJustProductionAsContributor(String title, Staff staff) {
//        if (staff.isContribution(title)) {
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
//        } else {
//            System.out.println("You can't update this production");
//            throw new RuntimeException("Invalid production");
//        }
    }
}
