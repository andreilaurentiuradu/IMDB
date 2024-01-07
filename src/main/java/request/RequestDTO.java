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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(createdDate, formatter);

        Request request = new Request();

        request.setRequesterUsername(username);
        request.setType(type);
        request.setDescription(description);
        request.setCreationDate(localDateTime);
        request.setProductionName(movieTitle);
        request.setActorName(actorName);
        request.setSolverUsername(to);

        return request;
    }
}
