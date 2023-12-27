package request;

import user.staff.Admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private RequestType type;
    private LocalDateTime creationDate;
    private String productionName;
    private String actorName;
    private String description;
    private String requesterUsername;
    private String solverUsername;

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", creationDate=" + creationDate +
                ", productionName='" + productionName + '\'' +
                ", actorName='" + actorName + '\'' +
                ", description='" + description + '\'' +
                ", requesterUsername='" + requesterUsername + '\'' +
                ", solverUsername='" + solverUsername + '\'' +
                '}';
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getSolverUsername() {
        return solverUsername;
    }

    public void setSolverUsername(String solverUsername) {
        this.solverUsername = solverUsername;
    }

    //    public LocalDateTime getCreationDate
    public static class RequestsHolder {
        private final List<Admin> admins = new ArrayList<>();

        public void addAdmin(Admin admin) {
            admins.add(admin);
        }

        public void removeAdmin(Admin admin) {
            admins.remove(admin);
        }

        public List<Admin> getAdmins() {
            return new ArrayList<>(admins);
        }
    }
}
