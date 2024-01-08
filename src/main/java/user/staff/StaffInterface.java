package user.staff;

import production.Production;
import production.details.Actor;
import request.Request;

import java.util.List;

public interface StaffInterface {
    void addProductionSystem(Production p);
    void addActorSystem(Actor a);
    void removeProductionSystem(String name);
    void removeActorSystem(String name);
    void updateProduction(Production p);
    void updateActor(Actor a);

    boolean isAllowedToUpdate(String value);

    void viewMediaIndustryUserCanUpdate();

    List<Request> getResolvableRequests();
}
