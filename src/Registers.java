public class Registers {
    // General Purpose Registers (gpr0 to gpr3)
    private static String gpr0;
    private static String gpr1;
    private static String gpr2;
    private static String gpr3;

    // Instruction Register
    private static String ir;

    // Memory Address Register
    private static String mar;

    // Memory Buffer Register
    private static String mbr;

    // Machine Fault Register
    private static String mfr;

    // Machine Status Register
    private static String msr;

    // Program Counter
    private static String pc;

    // Index Registers (ixr1 to ixr3)
    private static String ixr1;
    private static String ixr2;
    private static String ixr3;

    private static String cc; // condition code

    // Constructor to initialize the registers
    public Registers() {
        gpr0 = "0000000000000000";
        gpr1 = "0000000000000000";
        gpr2 = "0000000000000000";
        gpr3 = "0000000000000000";
        ir = "0000000000000000";
        mar = "0000000000000000";
        mbr = "0000000000000000";
        mfr = "0000000000000000";
        msr = "0000000000000000";
        pc = "0000000000000000";
        ixr1 = "0000000000000000";
        ixr2 = "0000000000000000";
        ixr3 = "0000000000000000";
        cc = "0000000000000000";
    }

    // Function to change the value of a register
    public static void setRegisterValue(String registerName, String value) {
        switch (registerName) {
            case "gpr0":
                gpr0 = value;
                break;
            case "gpr1":
                gpr1 = value;
                break;
            case "gpr2":
                gpr2 = value;
                break;
            case "gpr3":
                gpr3 = value;
                break;
            case "ir":
                ir = value;
                break;
            case "mar":
                mar = value;
                break;
            case "mbr":
                mbr = value;
                break;
            case "mfr":
                mfr = value;
                break;
            case "msr":
                msr = value;
                break;
            case "pc":
                pc = value;
                break;
            case "ixr1":
                ixr1 = value;
                break;
            case "ixr2":
                ixr2 = value;
                break;
            case "ixr3":
                ixr3 = value;
                break;
            case "cc":
                cc = value;
            default:
                System.out.println("Invalid register name");
        }
    }

    // Function to get the value of a register
    public static String getRegisterValue(String registerName) {
        switch (registerName) {
            case "gpr0":
                return gpr0;
            case "gpr1":
                return gpr1;
            case "gpr2":
                return gpr2;
            case "gpr3":
                return gpr3;
            case "ir":
                return ir;
            case "mar":
                return mar;
            case "mbr":
                return mbr;
            case "mfr":
                return mfr;
            case "msr":
                return msr;
            case "pc":
                return pc;
            case "ixr1":
                return ixr1;
            case "ixr2":
                return ixr2;
            case "ixr3":
                return ixr3;
            case "cc":
                return cc;
            default:
                System.out.println("Invalid register name");
                return "-1"; // Return a default value indicating an error
        }
    }
}
