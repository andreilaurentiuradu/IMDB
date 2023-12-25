package user;

import production.Production;

import java.time.LocalDateTime;
import java.util.*;

public abstract class User {
    private Information information;
    private AccountType accountType;
    private String username;
    private int experience;
    private List<String> notifications;
    private final SortedSet<Production> favorites = new TreeSet<>();

    public void addProduction(Production production) {
        favorites.add(production);
    }

    public void removeProduction(Production production) {
        favorites.remove(production);
    }

    public void updateUserExperience(int userExperience) {
        experience = userExperience;
    }

    // delogare si revenire la pagina de logare

    private static class Information {
        private Credentials credentials;
        private String name;
        private String country;
        private Integer age;
        private Character gender;
        private LocalDateTime birthday;
    }
}
