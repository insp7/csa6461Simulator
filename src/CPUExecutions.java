
public class CPUExecutions {
    // Instruction Execution Logic
    public static String perform_action(String dataStr) {
        // Splitting the input into instruction and parameters
        String[] parts = dataStr.split("\\|");
        if (parts.length != 2) {
            return "Invalid input format";
        }
        String instruction = parts[0].trim();//Remove whitespaces if any
        String parameters = parts[1].trim();//Remove whitespaces if any
        // Extracting individual bits for parameters
        String R = parameters.substring(6, 8);
        String IX = parameters.substring(8, 10);
        String I = parameters.substring(10, 11);
        String Address = parameters.substring(11, 16); // Assuming the substring includes the 5th to the 9th index (exclusive)
        System.out.println("String R: " + R + " String IX: " + IX + " String I: " + I + " String Address: " + Address);
        int AddressDecimal = Integer.parseInt(Address, 2); //initialize address in decimal form outside of switch condition
        // Initialize values of mar and mbr in order to access memory THIS SHOULD BE DONE IN MACHINE SIMULATOR
        Memory.load(Registers.getRegisterValue("pc")); //This loads the String value of PC into MAR (FIX?)
        switch (instruction) {
            case "HLT": {
                // PRESS HALT BUTTON STOP READING INSTRUCTION
                break; // Don't forget to break here
            }

            case "DATA": {
                // Retrieve Current Address and Store into Memory
                System.out.println("Executing DATA instructions...");
                Memory.store(Registers.getRegisterValue("mar"), parameters);
                break; // Don't forget to break here
            }
            case "LDR": {
                // LDR r,x,address[,I] (load register from memory)
                // Opcode R  IX I Address
                // 000001 xx xx x xxxxx
                System.out.println("Executing LDR instructions...");
                // Determine if an index register is used
                switch (IX) {
                    case "01" -> {
                        // Get value at IX1 address
                        String binaryValue = Registers.getRegisterValue("ixr1");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        String binaryValue = Registers.getRegisterValue("ixr2");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        String binaryValue = Registers.getRegisterValue("ixr3");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                }
                // Indirect/direct bit addressing
                if (I.equals("1")) {
                    // GET VALUE AT LOCATION ADDRESS_DECIMAL AND SET THAT TO NEW
//                    System.out.println("String value of AddressDecimal: " + AddressDecimal);
//                    System.out.println("Value that will be treated as the new address: " + Memory.load(String.valueOf(AddressDecimal)));
                    AddressDecimal = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal))); // fetched in binary
                    AddressDecimal = BaseConversion.binaryToDecimal(AddressDecimal + "");
                }
                System.out.println("String value of AddressDecimal: " + AddressDecimal);
                System.out.println("Value at that address" + Memory.load(String.valueOf(AddressDecimal)));
                // This determines register destination of value
                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr0", Memory.load(String.valueOf(AddressDecimal)));
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr1", Memory.load(String.valueOf(AddressDecimal)));
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        Registers.setRegisterValue("gpr2", Memory.load(String.valueOf(AddressDecimal)));
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("gpr3", Memory.load(String.valueOf(AddressDecimal)));
                        break; // Don't forget to break here
                    }
                }
                System.out.println("ADDRESS USED TO ACCESS VALUE:  " + String.valueOf(AddressDecimal));
                break; // Don't forget to break here
            } // End of LDR case

            case "LDA": {
                // LDA r,x,address[,I] (load register with address)
                // Opcode R  IX I Address
                // 000011 xx xx x xxxxx
                System.out.println("Executing LDA instructions...");
                // Determine if an index register is used
                switch (IX) {
                    case "01" -> {
                        // Get value at IX1 address
                        String binaryValue = Registers.getRegisterValue("ixr1");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        String binaryValue = Registers.getRegisterValue("ixr2");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        String binaryValue = Registers.getRegisterValue("ixr3");
                        int decimalValue = Integer.parseInt(binaryValue, 2);
                        AddressDecimal += decimalValue;
                        break; // Don't forget to break here
                    }
                }
                // Indirect/direct bit addressing
                if (I.equals("1")) {
                    // GET VALUE AT LOCATION ADDRESS_DECIMAL AND SET THAT TO NEW ADDRESS_DECIMAL
                    AddressDecimal = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));
                }

                // This determines register destination of value
                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr0", BaseConversion.decimalToBinary(String.valueOf(AddressDecimal), 16));
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr1", BaseConversion.decimalToBinary(String.valueOf(AddressDecimal), 16));
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        Registers.setRegisterValue("gpr2", BaseConversion.decimalToBinary(String.valueOf(AddressDecimal), 16));
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("gpr3", BaseConversion.decimalToBinary(String.valueOf(AddressDecimal), 16));
                        break; // Don't forget to break here
                    }
                }
                break; // Don't forget to break here
            }// End of LDA case

            case "LDX": {
                // LDX x,address[,I] (load index register from memory)
                // Opcode R  IX I Address
                // 000100 00 xx x xxxxx
                // No requirement of jumping address IX is the only register
                System.out.println("Executing LDX instructions...");
                // Indirect/direct bit addressing
                if (I.equals("1")) {
                    // GET VALUE AT LOCATION ADDRESS_DECIMAL AND SET THAT TO NEW ADDRESS_DECIMAL
                    AddressDecimal = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));
                }

                // This determines register destination of value
                switch (IX) {
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("ixr1", Memory.load(String.valueOf(AddressDecimal)));
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        System.out.println("Target Address: " + String.valueOf(AddressDecimal));
                        System.out.println("Value at that Address: "+ Memory.load(String.valueOf(AddressDecimal)));
                        Registers.setRegisterValue("ixr2", Memory.load(String.valueOf(AddressDecimal)));
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("ixr3", Memory.load(String.valueOf(AddressDecimal)));
                    }
                }
                break; // Don't forget to break here
            } // End of LDX case
        }
        return null;
    }
}