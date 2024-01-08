package repository;

import request.Request;

import java.util.List;

public class RequestRepository {

    private final String ADMIN = "ADMIN";

    public RequestRepository(List<Request> requests) {
        for (Request request : requests) {
            if (request.getSolverUsername().equals(ADMIN)) {
                Request.RequestsHolder.addAdminRequest(request);
            }
        }
    }

    public void addRequestForAdmin(Request request) {
        request.setSolverUsername(ADMIN);
        Request.RequestsHolder.addAdminRequest(request);
    }

    public List<Request> getAdminRequests() {
        return Request.RequestsHolder.getAdminRequests();
    }

    public void removeAdminRequest(Request request) {
        Request.RequestsHolder.removeAdminRequest(request);
    }
}
