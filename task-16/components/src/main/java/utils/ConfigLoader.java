package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ConfigLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String configFile = Paths.get("1-2", "src", "main", "java", "configs", "AppConfig.json").toString();

    public JsonNode getCsvConfig() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(new File(configFile));

        return jsonNode.get("csv");
    }

    public JsonNode getAppConfig() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(new File(configFile));

        return jsonNode.get("appLogic");
    }
}