package services;

import exceptions.InvalidCommandException;
import interaction.MenuBoard;
import production.MediaIndustry;
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
                String genre = terminalInteraction.readString("What genre do you want to filter by?\n"+
                        "Action, Adventure, Comedy, Drama, Horror, SF, Fantasy, Romance,\n" +
                        "Mystery, Thriller, Crime, Biography, War, Cooking");

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
                productionRepository.printAllMovies();
                break;
            case 5:
                productionRepository.printAllSeries();
                break;
            case 6:
                String actorName = terminalInteraction.readString("Which actor?", "name");
                productionRepository.printProductionByActorName(actorName);
                break;
            case 7:
                Integer releaseYear = terminalInteraction.readInt("After which year?");
                productionRepository.printProductionAfterReleaseYear(releaseYear);
                break;
            case 8:
                String directorName = terminalInteraction.readString("Which director?", "name");
                productionRepository.printProductionByDirectorName(directorName);
                break;
            case 9:
                productionRepository.printAll();
                break;
            default:
                throw new InvalidCommandException("Invalid filter option");
        }
    }

    public void searchForVideo() {
        String value = terminalInteraction.readString("What do you want to search", "video");

        // Convertim șirul într-un array de caractere
        char[] chs = value.toCharArray();

        // Construim un nou șir fără spații
        StringBuilder textFaraSpatii = new StringBuilder();

        // Iterăm prin array-ul de caractere și adăugăm caracterele non-spațiu la noul șir
        for (char ch : chs) {
            if (ch != ' ') {
                textFaraSpatii.append(ch);
            }
        }
        value = textFaraSpatii.toString();
        MediaIndustry mediaIndustry = new MediaIndustry(value);
        mediaIndustry.searchVideo();
    }
}
