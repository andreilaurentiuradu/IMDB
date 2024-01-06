package request;

import exceptions.InvalidCommandException;

public enum RequestType {
    DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS;

    public static RequestType getRequestType(String label) {
        RequestType requestType;
        try {
            requestType = RequestType.valueOf(label);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid request type");
        }

        return requestType;
    }
}
