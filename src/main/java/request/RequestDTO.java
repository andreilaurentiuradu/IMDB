package request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestDTO {
    public RequestType type;
    public String createdDate;
    public String movieTitle;
    public String actorName;
    public String description;
    public String username;
    public String to;

    public Request toRequest() {
        Request request = new Request();
        request.setType(type);
        request.setProductionName(movieTitle);
        request.setActorName(actorName);
        request.setDescription(description);
        request.setRequesterUsername(username);
        request.setSolverUsername(to);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(createdDate, formatter);
        request.setCreationDate(localDateTime);

        return request;
    }
}
