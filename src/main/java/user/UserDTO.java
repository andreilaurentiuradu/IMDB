package user;

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
    public final List<String> productionsContribution = new ArrayList<>();
    public final List<String> actorsContribution = new ArrayList<>();
    public final List<String> favoriteProductions = new ArrayList<>();
    public final List<String> favoriteActors = new ArrayList<>();
    public final List<String> notifications = new ArrayList<>();

    public User toUser() {
        AccountType accountType = AccountType.fromLabel(userType);
        User user = createUserWithType(accountType);

        user.setUsername(username);
        user.setExperience(experience);
        user.setNotifications(notifications);
        user.setInformation(information.toInformation());
        user.setAccountType(accountType);

        return user;
    }

    private User createUserWithType(AccountType accountType) {

        switch (accountType) {
            case CONTRIBUTOR: {
                Contributor user = new Contributor();

                user.addContributions(productionsContribution, actorsContribution);
                user.addFavorites(favoriteProductions, favoriteActors);

                return user;
            }
            case ADMIN: {
                Admin user = new Admin();

                user.addContributions(productionsContribution, actorsContribution);
                user.addFavorites(favoriteProductions, favoriteActors);

                return user;
            }
            case REGULAR: {
                Regular user = new Regular();

                user.addFavorites(favoriteProductions, favoriteActors);

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
