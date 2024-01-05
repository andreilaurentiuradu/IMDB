package production.details;

public class Episode {
    private String name;
    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
