package user;

public enum Gender {
    M("Male"), F("Female"), N("N");

    final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender fromLabel(String label) {
        for (Gender gender : values()) {
            if (gender.label.equals(label)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Gender not found: " + label);
    }
}
