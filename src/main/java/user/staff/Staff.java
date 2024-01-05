package user.staff;

import production.MediaIndustry;
import request.Request;
import production.details.Actor;
import production.Production;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public abstract class Staff extends User implements StaffInterface {

    public List<Request> requests = new ArrayList<>();
    private SortedSet<MediaIndustry> addedIndustries;

    public Staff() {

    }
    public Staff(User user) {
        super(user.getInformation(), user.getAccountType(), user.getUsername(), user.getExperience(), user.getNotifications(), user.getFavorites());
    }
    @Override
    public void addProductionSystem(Production p) {
        addedIndustries.add(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        addedIndustries.add(a);
    }

    @Override
    public void removeProductionSystem(String title) {
        for (MediaIndustry mediaIndustry : addedIndustries) {
            if (((Production)(mediaIndustry)).getTitle().equals(title)) {
                addedIndustries.remove(mediaIndustry);
            }
        }
    }

    @Override
    public void removeActorSystem(String name) {
        for (MediaIndustry mediaIndustry : addedIndustries) {
            if (((Actor)(mediaIndustry)).getName().equals(name)) {
                addedIndustries.remove(mediaIndustry);
            }
        }
    }

    @Override
    public void updateProduction(Production p) {

    }

    @Override
    public void updateActor(Actor a) {

    }
}
