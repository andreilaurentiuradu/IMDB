package services;

import interaction.MenuBoard;
import production.MediaIndustry;
import production.details.Actor;
import production.details.Genre;
import repository.ActorRepository;
import repository.ProductionRepository;
import user.User;

import static services.ActionsService.terminalInteraction;

public class GeneralService {
    ActorRepository actorRepository;
    ProductionRepository productionRepository;

    public GeneralService(ActorRepository actorRepository, ProductionRepository productionRepository) {
        this.actorRepository = actorRepository;
        this.productionRepository = productionRepository;
    }

    public void manageFavorites(User currentUser) {
        String action;
        action = terminalInteraction.readString("Remove/Add", "services");
        String title = terminalInteraction.readString("What actor/production?", "name/title");

//        TODO: daca vrei sa faci modificarile asa, trebuie sa iei current user DIN BAZA DE DATE(findUserByUsername)
        if (action.equals("Add")) {
            currentUser.addMediaIndustry(new MediaIndustry(title));
        } else if (action.equals("Remove")) {
            currentUser.removeMediaIndustry(new MediaIndustry(title));
        } else {
            throw new RuntimeException("Action not found");
        }

        System.out.println("The new favorite list is:");
        System.out.println(currentUser.getFavorites());
    }

    public void search() {
        String title = terminalInteraction.readString(
                "What title/name do you want to search for?",
                "title/name");

        System.out.println("Possible answers could be: ");

        Actor actor = actorRepository.searchByName(title);
        if (actor != null)
            System.out.println(actor);

        productionRepository.searchByTitle(title);
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
        String action;
        MenuBoard.showFilterOptions();

        action = terminalInteraction.readString("Choose filter options", "services");

        filterProductions(action);
    }

    public void filterProductions(String action) {
        switch (action) {
            case "1":
                String genre = terminalInteraction.readString("What genre do you want?\nAction, Adventure, Comedy, Drama, Horror, SF, " +
                        "Fantasy,\nRomance, Mystery, Thriller, Crime, Biography, War, Cooking", "genre");

                productionRepository.printByGenre(Genre.valueOf(genre));
                break;

            case "2":
                String type = terminalInteraction.readString("Under/Over/Equal?", "type");
                String number = terminalInteraction.readString(type + " which number?", "number");

                productionRepository.printByRating(Double.valueOf(number), type);
                break;

            case "3":
                productionRepository.printAll();
                break;

            default:
                throw new RuntimeException("Invalid input");
        }
    }
}
