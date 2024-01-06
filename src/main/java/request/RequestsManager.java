package request;

import java.time.LocalDateTime;

public interface RequestsManager {
    Request createRequest(RequestType type, String description, LocalDateTime currentDate, String username, String value);

    void removeRequest (Request r);
}
