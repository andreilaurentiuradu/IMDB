package production.details;

import exceptions.InvalidCommandException;

public enum Genre {
    Action, Adventure, Comedy, Drama, Horror, SF,
    Fantasy, Romance, Mystery, Thriller, Crime, Biography, War,
    Cooking;

    public static Genre getGenreType(String label) {
        Genre type;
        try {
            type = Genre.valueOf(label);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid request type");
        }

        return type;
    }


    public static void printAll() {
        System.out.println(
                "Action, Adventure, Comedy, Drama, Horror, SF, Fantasy, Romance,\n" +
                "Mystery, Thriller, Crime, Biography, War, Cooking");
    }
}
