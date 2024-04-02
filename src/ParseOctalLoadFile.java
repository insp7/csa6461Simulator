import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;


public class ParseOctalLoadFile {

    LinkedHashMap<String, String> octalLoadPairs;

    ParseOctalLoadFile(File loadFile) {
        octalLoadPairs = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(loadFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by spaces
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    // Assuming first value is key and second is value after spaces
                    octalLoadPairs.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, String> getOctalLoadPairs() {
        return octalLoadPairs;
    }
}
