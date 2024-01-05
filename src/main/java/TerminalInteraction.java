import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalInteraction {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readString(String field) {
        String value;
        try {
            value = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the " + field + " from terminal", e);
        }
        return value;
    }
}
