package user.staff;

import javafx.util.Pair;
import production.MediaIndustry;
import production.Movie;
import production.Series;
import repository.ActorRepository;
import repository.ProductionRepository;
import request.Request;
import production.details.Actor;
import production.Production;
import user.User;

import java.util.*;

import static repository.UserRepository.SUPREME;
import static services.ActionsService.actorRepository;
import static services.ActionsService.productionRepository;

public abstract class Staff extends User implements StaffInterface {

    public List<Request> requests = new ArrayList<>();

    private final SortedSet<MediaIndustry> contributions = new TreeSet<>(new Comparator<MediaIndustry>() {
        @Override
        public int compare(MediaIndustry o1, MediaIndustry o2) {
            return o1.value.compareTo(o2.value);
        }
    });

    public Set<MediaIndustry> getContributions() {
        return contributions;
    }

    @Override
    public void addProductionSystem(Production p) {
        productionRepository.addProduction(p);
        contributions.add(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        actorRepository.addActor(a);
        contributions.add(a);
    }

    public boolean isContributorToMediaIndustry(String value) {
        for (MediaIndustry mediaIndustry : contributions) {
            if (mediaIndustry.value.equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateProduction(Production update) {
        Production production = productionRepository.searchByTitle(update.getTitle());
        if (update.getReleaseYear() != null) {
            production.setReleaseYear(update.getReleaseYear());
        } else if (update.getPlot() != null) {
            production.setPlot(update.getPlot());
        }
    }

    @Override
    public void updateActor(Actor update) {
        Actor actor = actorRepository.searchByName(update.getName());

        if (update.getBiography() != null) {
            actor.updateBiography(update.getBiography());
        }

        if (update.getPerformances().isEmpty()) {
            return;
        }

        Production production = productionRepository.searchByTitle(update.getName());

        actor.addPerformances(update.getPerformances().get(0));

        if (production == null) {
            Pair<String, String> pairTitleType = update.getPerformances().get(0);
            if (pairTitleType.getValue().equals("Movie")) {
                productionRepository.addProduction(new Movie(pairTitleType.getKey()));
            } else {
                productionRepository.addProduction(new Series(pairTitleType.getKey()));
            }
        } else {
            production.addActor(actor);
        }
    }

    public void addContributions(List<String> actorsName, List<String> productionTitles) {
        for (String s : actorsName) {
            contributions.add(new MediaIndustry(s));
        }

        for (String s : productionTitles) {
            contributions.add(new MediaIndustry(s));
        }
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    @Override
    public void removeProductionSystem(String title) {
        MediaIndustry mediaIndustry = new MediaIndustry(title);

        productionRepository.removeProduction(title);
        getContributions().remove(mediaIndustry);
    }

    @Override
    public void removeActorSystem(String name) {
        MediaIndustry mediaIndustry = new MediaIndustry(name);

        actorRepository.removeActor(name);
        getContributions().remove(mediaIndustry);
    }

    @Override
    public String toString() {
        return "username=" + getUsername() +
                "\n contributions=" + contributions;
//                "\n requests=" + requests +
//                "\n Created requests=" + getCreatedRequests();
    }

    public void addContribution(String username) {
        contributions.add(new MediaIndustry(username));
    }
}
