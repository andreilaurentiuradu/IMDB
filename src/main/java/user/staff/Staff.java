package user.staff;

import request.Request;
import production.details.Actor;
import production.Production;
import user.User;

import java.util.List;
import java.util.SortedSet;

public abstract class Staff extends User implements StaffInterface {

    private List<Request> requests;
    private SortedSet<MediaIndustry> mediaIndustries;

    @Override
    public void addProductionSystem(Production p) {

    }

    @Override
    public void addActorSystem(Actor a) {

    }

    @Override
    public void removeProductionSystem(String name) {

    }

    @Override
    public void removeActorSystem(String name) {

    }

    @Override
    public void updateProduction(Production p) {

    }

    @Override
    public void updateActor(Actor a) {

    }

    private static class MediaIndustry {
        Production production;
        Actor actor;
    }
}
