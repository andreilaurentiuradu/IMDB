package user.staff;

import production.MediaIndustry;
import request.Request;
import production.details.Actor;
import production.Production;
import user.User;

import java.util.*;

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

    public boolean isContribution(String contribution) {
        for (MediaIndustry mediaIndustry : contributions) {
            if (mediaIndustry.value.equals(contribution))
                return true;
        }
        return false;
    }

    @Override
    public void addProductionSystem(Production p) {
        contributions.add(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        contributions.add(a);
    }

    @Override
    public void removeProductionSystem(String title) {
        for (MediaIndustry mediaIndustry : contributions) {
            if (((Production)(mediaIndustry)).getTitle().equals(title)) {
                contributions.remove(mediaIndustry);
                return;
            }
        }
    }

    @Override
    public void removeActorSystem(String name) {
        for (MediaIndustry mediaIndustry : contributions) {
            if (((Actor)(mediaIndustry)).getName().equals(name)) {
                contributions.remove(mediaIndustry);
                return;
            }
        }
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
    public void updateProduction(Production p) {

    }

    @Override
    public void updateActor(Actor a) {

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
    public String toString() {
        return "username=" + getUsername() +
                "\n requests=" + requests +
                "\n Created requests=" + getCreatedRequests();
    }
}
