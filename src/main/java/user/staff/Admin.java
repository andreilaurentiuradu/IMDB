package user.staff;

import production.MediaIndustry;
import production.details.Actor;
import repository.ActorRepository;
import repository.ProductionRepository;
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
        System.out.println("Csupreme contriburions: " + SUPREME.getContributions());
        boolean answer = SUPREME.getContributions().remove(new MediaIndustry(name));
        System.out.println("Answer is: " + answer);
        if (answer) {
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

        System.out.println(getContributions());
        System.out.println(SUPREME.getContributions());

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
