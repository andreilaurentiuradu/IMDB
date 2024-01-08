package services;

import exceptions.InvalidCommandException;
import interaction.MenuBoard;
import production.details.Actor;
import production.details.Genre;
import repository.ActorRepository;
import repository.ProductionRepository;
import user.User;

import static services.ActionsService.terminalInteraction;

public class GeneralService {
    private final ActorRepository actorRepository;
    private final ProductionRepository productionRepository;

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

    private void filterProductions(int action) {
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
}
