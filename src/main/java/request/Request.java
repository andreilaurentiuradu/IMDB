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
    private final String description;
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
    public String toString() {
        StringBuilder sb = new StringBuilder("Request: ");

        if (type != null)
            sb.append("type=").append(type).append(", ");
        if (creationDate != null)
            sb.append("creationDate=").append(creationDate).append(", ");
        if (productionName != null)
            sb.append("productionName='").append(productionName).append('\'').append(", ");
        if (actorName != null)
            sb.append("actorName='").append(actorName).append('\'').append(", ");
        if (description != null)
            sb.append("description='").append(description).append('\'').append(", ");
        if (requesterUsername != null)
            sb.append("requesterUsername='").append(requesterUsername).append('\'').append(", ");
        if (solverUsername != null)
            sb.append("solverUsername='").append(solverUsername).append('\'');

        int length = sb.length();
        if (length > 10 && sb.charAt(length - 2) == ',') {
            sb.delete(length - 2, length);
        }

        return sb.toString();
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
        private static final List<Request> adminRequests = new ArrayList<>();

        public static void addAdminRequest(Request request) {
            adminRequests.add(request);
        }

        public static void removeAdminRequest(Request request) {
            adminRequests.remove(request);
        }

        public static List<Request> getAdminRequests() {
            return adminRequests;
        }
    }
}
