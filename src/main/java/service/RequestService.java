package service;

import request.Request;

import java.util.List;

public class RequestService {
    private final Request.RequestsHolder requestsHolder = new Request.RequestsHolder();

    public RequestService(List<Request> requests) {
        for (Request request : requests) {
            if (request.getSolverUsername().equals("ADMIN")) {
                requestsHolder.addAdminRequest(request);
            }
        }
    }

    public void addRequestForAdmin(Request request) {
        request.setSolverUsername("ADMIN");
        requestsHolder.addAdminRequest(request);
    }

    public List<Request> getAdminRequests() {
        return requestsHolder.getAdminRequests();
    }

    public void removeAdminRequest(Request request) {
        System.out.println("Before");
        requestsHolder.getAdminRequests().forEach(System.out::println);
        requestsHolder.getAdminRequests().remove(request);

        System.out.println("After");
        requestsHolder.getAdminRequests().forEach(System.out::println);
    }
}
