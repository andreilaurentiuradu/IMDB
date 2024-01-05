package production;

public class Movie extends Production {
    private Double duration;
    private Integer releaseYear;

    public Movie(String title) {
        super(title);
    }

    @Override
    public void displayInfo() {
        System.out.println("Movie: " + getTitle()+ "duration: " +
                duration + " " + "releaseYear: " + releaseYear);
    }

    @Override
    public int compareTo(Production o) {
        return title.compareTo(o.title);
    }
}
