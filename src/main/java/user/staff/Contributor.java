package user.staff;

import production.MediaIndustry;
import production.details.Actor;
import repository.ActorRepository;
import repository.ProductionRepository;
import request.Request;
import request.RequestType;
import request.RequestsManager;

import java.util.List;

import static repository.UserRepository.SUPREME;
import static services.ActionsService.*;

public class Contributor extends Staff implements RequestsManager {

    @Override
    public Request createRequest(RequestType type, String description, String username, String value) {
        Request request = new Request(type, description, username);

        if (type == RequestType.MOVIE_ISSUE) {
            request.setProductionName(value);

        } else if (type == RequestType.ACTOR_ISSUE) {
            request.setActorName(value);
        }

        return request;
    }

    @Override
    public void removeRequest(Request r) {
        requests.remove(r);
    }

    @Override
    public boolean isAllowedToUpdate(String value) {
        return getContributions().contains(new MediaIndustry(value));
    }

    @Override
    public void viewMediaIndustryUserCanUpdate() {
        System.out.println("Available resources to update:");

        System.out.println(getContributions());

        System.out.println();
    }

    @Override
    public List<Request> getResolvableRequests() {
        List<Request> currentUserRequests = userRepository.findStaffByUsername(getUsername()).requests;

        return printRequestsList(currentUserRequests);    }
}
