package user.staff;

import production.Production;
import production.details.Actor;

public interface StaffInterface {
    void addProductionSystem(Production p);
    void addActorSystem(Actor a);
    void removeProductionSystem(String name);
    void removeActorSystem(String name);
    void updateProduction(Production p);
    void updateActor(Actor a);
}
