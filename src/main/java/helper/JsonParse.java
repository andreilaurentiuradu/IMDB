package helper;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class JsonParse {

    public <T> List<T> parseList(String filename, Class<T> targetType) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File(this.getClass().getClassLoader().getResource(filename).getFile());
            JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, targetType);

            return mapper.readValue(file, type);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("File cannot be parsed: ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: ", e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from ", e);
        }
    }
}
