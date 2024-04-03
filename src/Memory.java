import java.util.LinkedHashMap;
import java.util.Map;

public class Memory {
    LinkedHashMap<String, String> memory;

    Memory() {
        memory = new LinkedHashMap<>();
    }

    public String load(String mar) {
        return memory.getOrDefault(mar, null); // Return null if mar key does not exist
    }

    public void store(String mar, String mbr) {
        memory.put(mar, mbr);
    }

    // Method to display all elements of the memory LinkedHashMap
    public void showMemoryContents() {
        System.out.println("Memory Contents:");
        for (Map.Entry<String, String> entry : memory.entrySet()) {
            System.out.println("Location: " + entry.getKey() + " Value: " + entry.getValue());
        }
    }
}
