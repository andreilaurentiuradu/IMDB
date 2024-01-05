package request;

import java.time.LocalDateTime;

public interface RequestsManager {
    Request createRequest(RequestType type, String description, LocalDateTime currentDate, String username);

    void removeRequest (Request r);
}
