package services;

import production.details.Actor;
import user.staff.Staff;

import static services.ActionsService.terminalInteraction;
import static services.RequestService.updateActorField;

public class ActorService {

    public void updateActor(Staff currentUser) {
        currentUser.viewMediaIndustryUserCanUpdate();

        String name = terminalInteraction.readString("Introduce actor name");

        if (!currentUser.isAllowedToUpdate(name)) {
            System.out.println("You are not allowed to update this resource");
            return;
        }

        Actor update = updateActorField(name);

        currentUser.updateActor(update);
    }
}
