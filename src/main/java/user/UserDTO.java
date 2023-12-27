package user;

import production.MediaIndustry;
import user.staff.Admin;
import user.staff.Contributor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    public InformationDTO information;
    public String userType;
    public String username;
    public int experience;
    public List<String> productionsContribution = new ArrayList<>();
    public List<String> actorsContribution = new ArrayList<>();
    public List<String> favoriteProductions = new ArrayList<>();
    public List<String> favoriteActors = new ArrayList<>();
    public List<String> notifications = new ArrayList<>();

    public User toUser() {
        AccountType accountType = AccountType.fromLabel(userType);
        User user = createUserWithType(accountType);

        user.setUsername(username);
        user.setExperience(experience);
        user.setInformation(information.toInformation());
        user.setAccountType(accountType);

        return user;
    }

    private User createUserWithType(AccountType accountType) {
        User user;

        switch (accountType) {
            case CONTRIBUTOR: {
                user = new Contributor();
                user.addFavoriteProductions(productionsContribution);
                user.addFavoriteProductions(favoriteProductions);
                user.addFavoriteActors(actorsContribution);
                user.addFavoriteActors(favoriteActors);

                return user;
            }
            case ADMIN: {
                user = new Admin();

                user.addFavoriteProductions(productionsContribution);
                user.addFavoriteActors(actorsContribution);

                return user;
            }
            case REGULAR: {
                user = new Regular();

                user.addFavoriteProductions(favoriteProductions);
                user.addFavoriteActors(favoriteActors);

                return user;
            }
            default: {
                throw new RuntimeException("Invalid user");
            }
        }
    }

    public static class InformationDTO {
        public CredentialsDTO credentials;
        public String name;
        public String country;
        public Integer age;
        public String gender;
        public String birthDate;

        public User.Information toInformation() {
            User.Information information = new User.Information();

            // setting the credentials
            Credentials credentials = new Credentials();
            credentials.setPassword(this.credentials.password);
            credentials.setEmail(this.credentials.email);
            information.setCredentials(credentials);

            information.setName(name);
            information.setCountry(country);
            information.setAge(age);
            information.setGender(Gender.fromLabel(gender));

            // setting the birthDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(this.birthDate, formatter);
            LocalTime localTime = LocalTime.now();
            LocalDateTime birthDate = LocalDateTime.of(localDate, localTime);
            information.setBirthday(birthDate);

            return information;
        }

        public static class CredentialsDTO {
            public String email;
            public String password;

            public Credentials toCredentials() {
                Credentials credentials = new Credentials();
                credentials.setEmail(email);
                credentials.setPassword(password);

                return credentials;
            }
        }
    }
}
