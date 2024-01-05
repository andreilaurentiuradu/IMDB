package request;

import java.time.LocalDateTime;

public interface RequestsManager {
    Request createRequest(String type, String description, LocalDateTime currentDate, String username);

    void removeRequest (Request r);
}
