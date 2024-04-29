import java.util.HashMap;
import java.util.Map;

public class CPU {
    private static final Map<String, String> opcodeToBaseInstruction = new HashMap<>();

    static {
        // Initialize the mapping of opcodes to base instructions
        // Miscellaneous Instructions
        opcodeToBaseInstruction.put("000000", "HLT");

        // Load/Store Instructions
        opcodeToBaseInstruction.put("000001", "LDR");
        opcodeToBaseInstruction.put("000010", "STR");
        opcodeToBaseInstruction.put("000011", "LDA");
        opcodeToBaseInstruction.put("000100", "LDX");
        opcodeToBaseInstruction.put("000101", "STX");

        // Transfer Instructions
        opcodeToBaseInstruction.put("100100", "SETCCE");
        opcodeToBaseInstruction.put("000110", "JZ");
        opcodeToBaseInstruction.put("000111", "JNE");
        opcodeToBaseInstruction.put("001000", "JCC");
        opcodeToBaseInstruction.put("001001", "JMA");
        opcodeToBaseInstruction.put("001010", "JSR");
        opcodeToBaseInstruction.put("001011", "RFS");
        opcodeToBaseInstruction.put("001100", "SOB");
        opcodeToBaseInstruction.put("001101", "JGE");

        // Arithmetic and Logical Instructions
        opcodeToBaseInstruction.put("001110", "AMR");
        opcodeToBaseInstruction.put("001111", "SMR");
        opcodeToBaseInstruction.put("010000", "AIR");
        opcodeToBaseInstruction.put("010001", "SIR");
        opcodeToBaseInstruction.put("010010", "MLT");
        opcodeToBaseInstruction.put("010011", "DVD");
        opcodeToBaseInstruction.put("010100", "TRR");
        opcodeToBaseInstruction.put("010101", "AND");
        opcodeToBaseInstruction.put("010110", "ORR");
        opcodeToBaseInstruction.put("010111", "NOT");
        opcodeToBaseInstruction.put("011000", "SRC");
        opcodeToBaseInstruction.put("011001", "RRC");

        // I/O Operations
        opcodeToBaseInstruction.put("011010", "IN");
        opcodeToBaseInstruction.put("011011", "OUT");
        opcodeToBaseInstruction.put("011100", "CHK");
    }

    public static String getBaseInstruction(String instruction) {
        // Split the instruction string into address and data
        String[] parts = instruction.split("\\s+");
        if (parts.length == 2) {
            String addressStr = parts[0];
            String dataStr = parts[1];

            // Convert address from octal to decimal
            int addressDecimal = Integer.parseInt(addressStr, 8);

            // Convert data from octal to binary
            int dataDecimal = Integer.parseInt(dataStr, 8);
            String dataBinary = Integer.toBinaryString(dataDecimal);

            // Pad binary string with zeros on the left side until it reaches 16 digits
            dataBinary = String.format("%16s", dataBinary).replace(' ', '0');

            // Get only the first 6 digits
            String dataBinary_first6 = dataBinary.substring(0, 6);
            // Check if base instruction is HLT
            if ("HLT".equals(opcodeToBaseInstruction.get(dataBinary_first6))) {
                // Check if address is at the end of memory
                int endOfMemory = 1024; // Assuming memory size is 1024, 2048 bytes or 4096 bytes
                if (addressDecimal >= endOfMemory) {
                    return "HLT" + "|" + dataBinary;
                } else {
                    return "DATA" + "|" + dataBinary;
                }
            } else {
                // Look up the opcode in the mapping
                String baseInstruction = opcodeToBaseInstruction.get(dataBinary_first6);
                // If base instruction is null, consider it a data instruction
                return baseInstruction != null ? baseInstruction + "|" + dataBinary : "DATA" + "|" + dataBinary;
            }
        } else {
            return "INVALID INSTRUCTION FORMAT";
        }
    }
    public static void main(String[] args){
        // Test cases
        String instruction1 = "000006 000012"; // Data    10 IDEALLY MAR IS THE FIRST ONE MBR IS THE SECOND, and PC tells MAR AND MBR WHAT LINE TO LOOK AT
        String instruction2 = "002002  067011"; // OUT     2,9
        String instruction3 = "000017  003412";// LDR     3,0,10    ;R3 GETS 12

        String instruction4 = "123456 000000"; // Unknown instruction format

        System.out.println(getBaseInstruction(instruction1)); // Output: LDR
        System.out.println(getBaseInstruction(instruction2)); // Output: LDR
        System.out.println(getBaseInstruction(instruction3));
        System.out.println(getBaseInstruction(instruction4));// Output: INVALID INSTRUCTION FORMAT
        //perform_action(instruction3);
    }
}