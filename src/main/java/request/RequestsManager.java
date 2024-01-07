package request;

public interface RequestsManager {
    Request createRequest(RequestType type, String description, String username, String value);

    void removeRequest (Request r);
}
