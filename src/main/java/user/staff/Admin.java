package user.staff;

import production.MediaIndustry;
import request.Request;
import user.AccountType;

import java.util.ArrayList;
import java.util.List;

import static repository.UserRepository.SUPREME;
import static services.ActionsService.*;

public class Admin extends Staff {

    public Admin() {}

    public Admin(String username) {
        super.setUsername(username);
        super.setCredentials("admin", "admin");
        super.setAccountType(AccountType.ADMIN);
    }

    @Override
    public void removeProductionSystem(String title) {
        actorRepository.removeActor(title);
        if (SUPREME.getContributions().remove(new MediaIndustry(title))) {
            System.out.println(SUPREME.getContributions());
            productionRepository.removeProduction(title);
            return;
        }

        super.removeProductionSystem(title);
    }

    @Override
    public void removeActorSystem(String name) {
        productionRepository.removeProduction(name);

        if (SUPREME.getContributions().remove(new MediaIndustry(name))) {
            System.out.println(SUPREME.getContributions());
            actorRepository.removeActor(name);
            return;
        }
        super.removeActorSystem(name);
    }

    @Override
    public boolean isAllowedToUpdate(String value) {
        return getContributions().contains(new MediaIndustry(value)) ||
                SUPREME.getContributions().contains(new MediaIndustry(value));
    }

    @Override
    public void viewMediaIndustryUserCanUpdate() {
        System.out.println("Available resources to update:");

        viewContributions();
        System.out.println();

        for (MediaIndustry mediaIndustry : SUPREME.getContributions()) {
            System.out.println(mediaIndustry.value);
        }

        System.out.println();
    }

    @Override
    public List<Request> getResolvableRequests() {
        List<Request> availableRequests = new ArrayList<>();
        List<Request> adminRequests = requestRepository.getAdminRequests();
        List<Request> currentUserRequests = userRepository.findStaffByUsername(getUsername()).requests;

        availableRequests.addAll(adminRequests);
        availableRequests.addAll(currentUserRequests);

        return printRequestsList(availableRequests);
    }
}
