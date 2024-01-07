package production;

import production.details.Episode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Series extends Production {
    private Integer seasonsNumber;
    private Map<String, List<Episode>> episodesBySeason = new HashMap<>();

    public Series(String title) {
        super(title);
    }

    public Integer getSeasonsNumber() {
        return seasonsNumber;
    }

    public void setSeasonsNumber(Integer seasonsNumber) {
        this.seasonsNumber = seasonsNumber;
    }

    public void addEpisodes(String serial, List<Episode> episode) {
        episodesBySeason.put(serial, episode);

    }

    public void setEpisodesBySeason(Map<String, List<Episode>> episodesBySeason) {
        this.episodesBySeason = episodesBySeason;
    }

    public Map<String, List<Episode>> getEpisodesBySeason() {
        return episodesBySeason;
    }

    @Override
    public void displayInfo() {
        printIfNotNull("Serial:", getTitle());

        displayCommonInfo();

        if (episodesBySeason != null) {
            System.out.println("\tIt has " + episodesBySeason.size() + " seasons:");
            for (Map.Entry<String, List<Episode>> entry : episodesBySeason.entrySet()) {
                System.out.println("\t\t" + entry.getKey());
                for (Episode episode : entry.getValue()) {
                    System.out.println("\t\t\t" + episode);
                }
            }
        }

        System.out.println();
    }

    @Override
    public int compareTo(Production o) {
        return title.compareTo(o.title);
    }
}
