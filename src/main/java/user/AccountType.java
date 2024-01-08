package user;

public enum AccountType {
    REGULAR("Regular"), CONTRIBUTOR("Contributor"), ADMIN("Admin");

    private final String label;

    AccountType(String type) {
        label = type;
    }

    public static AccountType fromLabel(String label) {
        for (AccountType accountType : values()) {
            if (accountType.label.equals(label)) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("User type not found: " + label);
    }
}
