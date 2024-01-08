package user;

import production.MediaIndustry;
import production.Production;
import production.details.Actor;
import request.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class User {
    private Information information = new Information();
    private AccountType accountType;
    private String username;
    private int experience;
    private List<String> notifications;
    private final List<MediaIndustry> favoriteActors = new ArrayList<>();
    private final List<MediaIndustry> favoriteProductions = new ArrayList<>();
    private SortedSet<MediaIndustry> favorites = new TreeSet<>(new Comparator<MediaIndustry>() {
        @Override
        public int compare(MediaIndustry o1, MediaIndustry o2) {
            return o1.value.compareTo(o2.value);
        }
    });
    private final List<Request> createdRequests = new ArrayList<>();

    public User() {
    }

    public User(Information information, AccountType accountType, String username, int experience, List<String> notifications, SortedSet<MediaIndustry> favorites) {
        this.information = information;
        this.accountType = accountType;
        this.username = username;
        this.experience = experience;
        this.notifications = notifications;
        this.favorites = favorites;
    }

    public List<Request> printRequestsList(List<Request> deletableRequests) {
        for (int i = 0; i < deletableRequests.size(); i++) {
            Request request = deletableRequests.get(i);
            if (!request.solved && !request.canceled) {
                System.out.print(i + 1 + ")");
                request.displayRequest();
            }
        }
        System.out.println();

        return deletableRequests;
    }

    public boolean isStaff() {
        return accountType == AccountType.CONTRIBUTOR || accountType == AccountType.ADMIN;
    }

    public List<Request> getCreatedRequests() {
        return createdRequests;
    }

    public void addCreatedRequest(Request request) {
        this.createdRequests.add(request);
    }

    public void removeCreatedRequest(Request request) {
        this.createdRequests.remove(request);
    }

    public void setCredentials(String email, String password) {
        this.information.setCredentials(new Credentials(email, password));
    }

    public Information getInformation() {
        return information;
    }

    public void createMediaIndustryFavorites(List<Actor> actors, List<Production> productions) {

        for (MediaIndustry mediaIndustry : favoriteActors) {
            favorites.add(Actor.getActorByName(actors, mediaIndustry.value));
        }

        for (MediaIndustry mediaIndustry : favoriteProductions) {
            favorites.add(Production.getProductionByTitle(productions, mediaIndustry.value));
        }
    }

    public void addFavorites( List<String> productions, List<String> actors) {
        for (String s : actors) {
            favoriteActors.add(new MediaIndustry(s));
        }

        for (String s : productions) {
            favoriteProductions.add(new MediaIndustry(s));
        }
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public SortedSet<MediaIndustry> getFavorites() {
        return favorites;
    }

    public void displayInfo() {
        System.out.println(accountType + " " +  information);
    }

    public void displayNewUserInfo() { // also includes credentials
        System.out.println();
        System.out.println("Created account :" + username + " with type " + accountType);

        displayAllUserInformation();
    }

    public void addMediaIndustry(MediaIndustry media) {
        favorites.add(media);
    }

    public void removeMediaIndustry(MediaIndustry media) {
        favorites.remove(media);
    }

    public void updateCountry(String country) {
        information.setCountry(country);
    }

    public void updateAge(int age) {
        information.setAge(age);
    }

    public void updateBirthday(LocalDate localDate) {
        LocalTime localTime = LocalTime.now();
        LocalDateTime birthDate = LocalDateTime.of(localDate, localTime);
        information.setBirthday(birthDate);

    }

    private void displayAllUserInformation() {

        printIfNotNull("\temail:", information.credentials.getEmail());
        printIfNotNull("\tpassword:", information.credentials.getPassword());
        printIfNotNull("\tcountry:", information.country);

        if (information.age != null) {
            System.out.println("\tage:" + information.age);
        }

        if (information.gender != null) {
            System.out.println("\tgender:" + information.gender);
        }

        if (information.birthday != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("\tbirthday:" + information.birthday.format(formatter));
        }
    }

    public void printIfNotNull(String message, String value) {
        if (value != null)
            System.out.println(message + " " + value);
    }

    public static class Information {
        private Credentials credentials;
        private String name;
        private String country;
        private Integer age;
        private Gender gender;
        private LocalDateTime birthday;

        public Information() {
        }

        private Information(InformationBuilder informationBuilder) {
            this.credentials = informationBuilder.credentials;
            this.name = informationBuilder.name;
            this.country = informationBuilder.country;
            this.age = informationBuilder.age;
            this.gender = informationBuilder.gender;
            this.birthday = informationBuilder.birthday;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }

        public static class InformationBuilder {
            private final Credentials credentials;
            private String name;
            private String country;
            private Integer age;
            private Gender gender;
            private LocalDateTime birthday;

            public InformationBuilder(Credentials credentials) {
                this.credentials = credentials;
            }

            public InformationBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder setAge(Integer age) {
                this.age = age;
                return this;
            }

            public InformationBuilder setGender(Gender gender) {
                this.gender = gender;
                return this;
            }

            public InformationBuilder setBirthday(LocalDateTime birthday) {
                this.birthday = birthday;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
