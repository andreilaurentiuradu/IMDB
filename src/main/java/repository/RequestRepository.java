package repository;

import request.Request;

import java.util.List;

public class RequestRepository {

    public RequestRepository(List<Request> requests) {
        for (Request request : requests) {
            if (request.getSolverUsername().equals("ADMIN")) {
                Request.RequestsHolder.addAdminRequest(request);
            }
        }
    }

    public void addRequestForAdmin(Request request) {
        request.setSolverUsername("ADMIN");
        Request.RequestsHolder.addAdminRequest(request);
    }

    public List<Request> getAdminRequests() {
        return Request.RequestsHolder.getAdminRequests();
    }

    public void removeAdminRequest(Request request) {
        System.out.println("Before");
        Request.RequestsHolder.getAdminRequests().forEach(System.out::println); // TODO debug
        Request.RequestsHolder.removeAdminRequest(request);

        System.out.println("After");
        Request.RequestsHolder.getAdminRequests().forEach(System.out::println);
    }
}
