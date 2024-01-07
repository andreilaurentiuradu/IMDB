package services;

import interaction.MenuBoard;
import production.MediaIndustry;
import production.Movie;
import production.Series;
import production.details.Actor;
import production.details.Genre;
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
            System.out.println(actor);

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

    public void addOrRemoveMediaIndustryFromSystem(User currentUser) {
        String action;
        action = terminalInteraction.readString("Add/Remove", "action");

        if (action.equals("Remove")) {
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

            }
            else {
                String value;
                value = terminalInteraction.readString("Introduce title/name for removing", "type");
                productionRepository.removeProduction(value);
                actorRepository.removeActor(value);
            }

        } else if (action.equals("Add")) {
            String type;
            type = terminalInteraction.readString("Actor/Movie/Series", "type");
            String value;
            value = terminalInteraction.readString("Introduce title/name for adding", "type");

            if (type.equals("Movie")) {
                productionRepository.addProduction(new Movie(value));
            } else if (type.equals("Series")){
                productionRepository.addProduction(new Series(value));
            } else if (type.equals("Actor")){
                actorRepository.addActor(new Actor(value));
            } else {
                throw new RuntimeException("Invalid input");
            }
        }
    }
}
