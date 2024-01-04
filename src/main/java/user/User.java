package user;

import production.MediaIndustry;
import production.Production;
import production.details.Actor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class User {
    private Information information;
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

    public Information getInformation() {
        return information;
    }

    public void createFavorites(List<Actor> actors, List<Production> productions) {

        for (MediaIndustry mediaIndustry : favoriteActors) {
            favorites.add(Actor.getActorByName(actors, mediaIndustry.value));
        }

        for (MediaIndustry mediaIndustry : favoriteProductions) {
            favorites.add(Production.getProductionByTitle(productions, mediaIndustry.value));
        }
    }

    public void addFavoriteActors(List<String> actors) {
        for (String s : actors) {
            favoriteActors.add(new MediaIndustry(s));
        }
    }

    public void addFavoriteProductions(List<String> productions) {
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

    @Override
    public String toString() {
        return "User{" +
                "information=" + information +
                ", accountType=" + accountType +
                ", username='" + username + '\'' +
                ", experience=" + experience +
                ", notifications=" + notifications +
                ", favorites=" + favorites +
                '}';
    }

    public void addMediaIndustry(MediaIndustry media) {
        favorites.add(media);
    }

    public void removeMediaIndustry(MediaIndustry media) {
        favorites.remove(media);
    }

    public void updateUserExperience(int userExperience) {
        experience = userExperience;
    }

    // delogare si revenire la pagina de logare

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public LocalDateTime getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return "Information{" +
                    "credentials=" + credentials +
                    ", name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", birthday=" + birthday.format(formatter) +
                    '}';
        }

        public static class InformationBuilder {
            private Credentials credentials;
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

            public InformationBuilder setCountry(String country) {
                this.country = country;
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
