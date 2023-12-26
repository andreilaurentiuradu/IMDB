package production;

import production.details.Episode;

import java.util.List;
import java.util.Map;
public class Series extends Production {
    private Integer releaseYear;
    private Integer seasonsNumber;
    private Map<String, List<Episode>> episodesBySeason;

    @Override
    public void displayInfo() {
        System.out.println("Series:" + getTitle());
    }
}
