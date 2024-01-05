package request;

import user.staff.Admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Request {
    private RequestType type;
    private LocalDateTime creationDate;
    private String productionName;

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    private String actorName;
    private String description;
    private String requesterUsername;
    private String solverUsername;
     public boolean solved;

    public Request (RequestType type, String description, LocalDateTime currentDate, String username) {
        this.type = type;
        this.description = description;
        this.requesterUsername = username;
        this.creationDate = currentDate;

        if (type == RequestType.DELETE_ACCOUNT || type == RequestType.OTHERS) {
            this.solverUsername = "ADMIN";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return solved == request.solved && type == request.type && Objects.equals(productionName, request.productionName) && Objects.equals(actorName, request.actorName) && Objects.equals(description, request.description) && Objects.equals(requesterUsername, request.requesterUsername) && Objects.equals(solverUsername, request.solverUsername);
    }

    @Override
    public String toString() {
        return "Request: " +
                "type=" + type +
//                ", creationDate=" + creationDate +
                ", productionName='" + productionName + '\'' +
                ", actorName='" + actorName + '\'' +
                ", description='" + description + '\'' +
                ", requesterUsername='" + requesterUsername + '\'' +
                ", solverUsername='" + solverUsername;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getSolverUsername() {
        return solverUsername;
    }

    public void setSolverUsername(String solverUsername) {
        this.solverUsername = solverUsername;
    }

    public static class RequestsHolder {
        private final List<Request> adminRequests = new ArrayList<>();

        public void addAdminRequest(Request request) {
            adminRequests.add(request);
        }

        public void removeAdminRequest(Request request) {
            adminRequests.remove(request);
        }

        public List<Request> getAdminRequests() {
            return adminRequests;
        }
    }
}
