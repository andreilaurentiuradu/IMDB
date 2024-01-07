package interaction;

public class MenuBoard {

    public static void showAdminActions() {
        showGeneralActions();

        System.out.println("6) Add/Remove production/actor");
        System.out.println("7) Solve a request");
        System.out.println("8) Update production/actor info");
        System.out.println("9) Add/Delete user");
        System.out.println("10) Logout/Exit");
    }

    public static void showContributorActions() {
        showGeneralActions();

        System.out.println("6) Create/Discard a request");
        System.out.println("7) Add/Remove production/actor");
        System.out.println("8) Solve a request");
        System.out.println("9) Update production/actor info");
        System.out.println("10) Logout/Exit");
    }

    public static void showRegularActions() {
        showGeneralActions();

        System.out.println("6) Create/Discard a request");
        System.out.println("7) Add/Delete review");
        System.out.println("8) Logout/Exit");
    }

    private static void showGeneralActions() {
        System.out.println("\nChoose action by pressing the correspondent number:");
        System.out.println("1) View productions details");
        System.out.println("2) View actors details");
        System.out.println("3) View notifications");
        System.out.println("4) Search for Actor/Movie/Series");
        System.out.println("5) Remove/Add Production/Actor from/to favorites List");
    }

    public static void showFilterOptions() {
        System.out.println("Filter results by:");
        System.out.println("1) Genre");
        System.out.println("2) Ratings");
        System.out.println("3) Don't filter");
    }
}
