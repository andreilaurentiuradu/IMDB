package production;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MediaIndustry {
    public final String value; // title or name

    public MediaIndustry (String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        MediaIndustry that = (MediaIndustry) o;
        return Objects.equals(value, that.value);
    }

    public void searchVideo() {
        String apiKey = "AIzaSyC3c9onCjkh5Oi4Yond_QmBw18Q9uYpUF4";

        try {
            // Construiți URL-ul pentru cererea către YouTube Data API
            String apiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + value +
                    "&type=video&key=" + apiKey;
            URL url = new URL(apiUrl);

            // Creează conexiune HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Setează metoda de cerere
            connection.setRequestMethod("GET");

            // Primește răspunsul de la server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Parsează răspunsul JSON
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray items = jsonResponse.getAsJsonArray("items");

            // Obține primul rezultat și extrage ID-ul videoclipului
            if (!items.isEmpty()) {
                JsonObject firstResult = items.get(0).getAsJsonObject();
                String videoId = firstResult.getAsJsonObject("id").get("videoId").getAsString();

                // Construiește link-ul către videoclipul de pe YouTube
                String youtubeLink = "https://www.youtube.com/watch?v=" + videoId;
                System.out.println("Link: " + youtubeLink);
            } else {
                System.out.println("No video found \"" + value + "\".");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
