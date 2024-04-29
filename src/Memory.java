import java.util.LinkedHashMap;
import java.util.Map;

public class Memory {
    static LinkedHashMap<String, String> memory;
    final static int SIZE = 4096;

    public Memory() {
        memory = new LinkedHashMap<>();
        // initialize the memory
        for (int i = 0; i < SIZE; i++) {
            String location = BaseConversion.decimalToOctal(i, 6);
            memory.put(location, "000000");
        }
    }

    /**
     *
     * @param mar Takes mar in decimal value
     * @return returns octal number
     */
    public static String load(String mar) {
        // Left-pad mar with zeros until it reaches 6 digits
        String octMar = BaseConversion.decimalToOctal(Integer.parseInt(mar), 6);
//        mar = String.format("%06d", Integer.parseInt(mar));
        return memory.getOrDefault(octMar, null); // Return null if mar key does not exist
    }

    public static void store(String mar, String mbr) {
        memory.put(mar, mbr);
    }

    // Method to display all elements of the memory LinkedHashMap
    public static void showMemoryContents() {
        System.out.println("Memory Contents:");
        int count = 0;
        for (Map.Entry<String, String> entry : memory.entrySet()) {
            if (count >= 20) { // remove later
                break;
            }
            System.out.println("Location: " + entry.getKey() + " Value: " + entry.getValue());
            count++; //remove later
        }
    }

    public static void reset() {
        memory = null; // reset
        memory = new LinkedHashMap<>();
        // initialize the memory again
        for (int i = 0; i < SIZE; i++) {
            String location = BaseConversion.decimalToOctal(i, 6);
            memory.put(location, "000000");
        }
    }
}