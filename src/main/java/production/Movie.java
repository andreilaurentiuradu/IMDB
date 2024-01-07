package production;

public class Movie extends Production {
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Movie(String title) {
        super(title);
    }

    @Override
    public void displayInfo() {
        printIfNotNull("Movie:", getTitle());
        printIfNotNull("\tDuration:", String.valueOf(duration));

        displayCommonInfo();

        System.out.println();
    }

    @Override
    public int compareTo(Production o) {
        return title.compareTo(o.title);
    }
}
