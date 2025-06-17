import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class ChessConfigLoader {
    public static ChessConfig loadFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ChessConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
