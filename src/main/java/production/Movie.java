package production;

public class Movie extends Production {
    private Double duration;
    private Integer releaseYear;

    @Override
    public void displayInfo() {
        System.out.println("Movie: " + getTitle()+ "duration: " +
                duration + " " + "releaseYear: " + releaseYear);
    }
}
