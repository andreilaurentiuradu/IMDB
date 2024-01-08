package interaction;

import exceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalInteraction {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readString(String message) {
        System.out.println(message);
        String value;
        try {
            value = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the from terminal", e);
        }
        return value;
    }

    public String readString(String message, String field) {
        System.out.println(message);
        String value;
        try {
            value = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the " + field + " from terminal", e);
        }
        return value;
    }

    public int readInt(String message) {
        System.out.println(message);

        int filterOption;
        try {
            String value = reader.readLine();
            filterOption = Integer.parseInt(value);

        } catch (IOException e) {
            throw new RuntimeException("Can't read the number from terminal", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number", e);

        }

        return filterOption;
    }

    public int chosenOperation() {
        System.out.println("Choose action number");

        int operationNumber;
        try {
            String value = reader.readLine();
            operationNumber = Integer.parseInt(value);

            if (operationNumber < 1) {
                throw new InvalidCommandException("Invalid operation number");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read the action from terminal", e);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Invalid operation number");

        }

        return operationNumber;
    }
}
