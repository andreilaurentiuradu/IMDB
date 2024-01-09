package request;

import user.notifications.Observer;
import user.notifications.Subject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Request implements Subject {
    public final List<Observer> observers = new ArrayList<>();
    private RequestType type;
    private LocalDateTime creationDate;
    private String productionName;
    private String actorName;
    private String description;
    private String requesterUsername;
    private String solverUsername;
    public boolean solved;
    public boolean canceled;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String notification) {
        System.out.println("NOTIFICAT");
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getProductionName() {
        return productionName;
    }

    public String getActorName() {
        return actorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Request() {
     }

    public Request (RequestType type, String description, String username) {
        this.type = type;
        this.description = description;
        this.requesterUsername = username;
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);;

        if (type == RequestType.DELETE_ACCOUNT || type == RequestType.OTHERS) {
            this.solverUsername = "ADMIN";
        }
    }

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
                ", solved=" + solved +
                ", canceled=" + canceled +
                '}';
    }

    public void displayRequest() {
        printIfNotNull("Type", String.valueOf(type));
        printIfNotNull("\tDescription", description);
        printIfNotNull("\tProduction name", productionName);
        printIfNotNull("\tActor name", actorName);
        printIfNotNull("\tRequester username", requesterUsername);
        printIfNotNull("\tSolver username", solverUsername);
        printIfNotNull("\tCreation date", String.valueOf(type));
    }

    private void printIfNotNull(String message, String value) {
        if (value != null)
            System.out.println(message + " " + value);
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
