
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

            case "AND": {
                // Assuming `rx` and `ry` are represented by their binary values stored in the registers
                // AND rx,ry (logical And of register and register)
                // Opcode Rx Ry ------
                // 010101 xx xx 000000
                System.out.println("Executing AND instructions...");
                String binaryValueRx = "";
                String binaryValueRy = "";

                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        binaryValueRx = Registers.getRegisterValue("gpr0");
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        binaryValueRx = Registers.getRegisterValue("gpr1");
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        binaryValueRx = Registers.getRegisterValue("gpr2");
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        binaryValueRx = Registers.getRegisterValue("gpr3");
                        break; // Don't forget to break here
                    }
                }
                switch (IX) {
                    case "00" -> {
                        // Get value at IX1 address
                        binaryValueRy = Registers.getRegisterValue("gpr0");
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        binaryValueRy = Registers.getRegisterValue("gpr1");
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        binaryValueRy = Registers.getRegisterValue("gpr2");
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        binaryValueRy = Registers.getRegisterValue("gpr3");
                        break; // Don't forget to break here
                    }
                }
                // Ensure binary values have the same length by left-padding with zeros if necessary
                int maxLength = Math.max(binaryValueRx.length(), binaryValueRy.length());
                binaryValueRx = String.format("%" + maxLength + "s", binaryValueRx).replace(' ', '0');
                binaryValueRy = String.format("%" + maxLength + "s", binaryValueRy).replace(' ', '0');

                // Initialize variables to track the smaller binary value
                String smallerBinaryValue = binaryValueRx;

                // Compare binary values character by character to find the smaller value
                for (int i = 0; i < maxLength; i++) {
                    char charRx = binaryValueRx.charAt(i);
                    char charRy = binaryValueRy.charAt(i);

                    if (charRx < charRy) {
                        smallerBinaryValue = binaryValueRx;
                        break;
                    } else if (charRx > charRy) {
                        smallerBinaryValue = binaryValueRy;
                        break;
                    }
                }

                // Set the registers IX and Ry to the smaller binary value
                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr0", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr1", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        Registers.setRegisterValue("gpr2", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("gpr3", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                }

                switch (IX) {
                    case "00" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr0", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr1", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        Registers.setRegisterValue("gpr2", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("gpr3", smallerBinaryValue);
                        break; // Don't forget to break here
                    }
                }
                break; // Don't forget to break here
            } // END OF AND CASE

            case "SMR": {
                // SMR r,x,address[,I] (subtract memory from register)
                // Opcode r  IX I Address
                // 001111 xx xx x xxxxx
                System.out.println("Executing SMR instructions...");
                int R_decimal_value = 0;
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
                // System.out.println("String value of AddressDecimal: " + AddressDecimal);
                // System.out.println("Value at that address" + Memory.load(String.valueOf(AddressDecimal)));
                // This determines register destination of value
                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        String R_binary_value = Registers.getRegisterValue("gpr0");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        String R_binary_value = Registers.getRegisterValue("gpr1");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        String R_binary_value = Registers.getRegisterValue("gpr2");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        String R_binary_value = Registers.getRegisterValue("gpr3");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        break; // Don't forget to break here
                    }
                }
                int value_at_address = BaseConversion.binaryToDecimal(Memory.load(String.valueOf(AddressDecimal)));
                // Calculate the absolute difference
                int absoluteDifference = Math.abs(R_decimal_value - value_at_address);

                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr0", String.valueOf(absoluteDifference));
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        Registers.setRegisterValue("gpr1", String.valueOf(absoluteDifference));
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        Registers.setRegisterValue("gpr2", String.valueOf(absoluteDifference));
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        Registers.setRegisterValue("gpr3", String.valueOf(absoluteDifference));
                        break; // Don't forget to break here
                    }
                }

                System.out.println("ADDRESS USED TO ACCESS VALUE:  " + String.valueOf(AddressDecimal));
                break; // Don't forget to break here

            } // End of SMR case

            case "AIR" : {
                // AIR r,Immed (add immediate to register)
                // Opcode R  IX I Address
                // 010000 xx 00 0 xxxxx
                System.out.println("Executing SMR instructions...");
                int R_decimal_value = 0;

                switch (R) {
                    case "00" -> {
                        // Get value at IX1 address
                        String R_binary_value = Registers.getRegisterValue("gpr0");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        Registers.setRegisterValue("grp0",String.valueOf(R_decimal_value + AddressDecimal));
                        break; // Don't forget to break here
                    }
                    case "01" -> {
                        // Get value at IX1 address
                        String R_binary_value = Registers.getRegisterValue("gpr1");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        Registers.setRegisterValue("grp1",String.valueOf(R_decimal_value + AddressDecimal));
                        break; // Don't forget to break here
                    }
                    case "10" -> {
                        // Get value at IX2 address
                        String R_binary_value = Registers.getRegisterValue("gpr2");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        Registers.setRegisterValue("grp2",String.valueOf(R_decimal_value + AddressDecimal));
                        break; // Don't forget to break here
                    }
                    case "11" -> {
                        // Get value at IX3 address
                        String R_binary_value = Registers.getRegisterValue("gpr3");
                        R_decimal_value = BaseConversion.binaryToDecimal(R_binary_value);
                        Registers.setRegisterValue("grp3",String.valueOf(R_decimal_value + AddressDecimal));
                        break; // Don't forget to break here
                    }
                }
            }

            case "JMA" : {
                // JMA x,address[,I] (unconditional jump to address)
                // Opcode R  IX I Address
                // 001001 00 xx x xxxxx
                // Determine the effective address based on the presence of indirect addressing
                int effectiveAddress;
                if (I.equals("1")) {
                    // Indirect addressing: load value at the given address
                    effectiveAddress = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));
                } else {
                    // Direct addressing: use the address directly
                    effectiveAddress = Integer.parseInt(String.valueOf(AddressDecimal));
                }

                // Perform the jump operation
                Registers.setRegisterValue("pc", BaseConversion.decimalToBinary(String.valueOf(effectiveAddress), 16));

                break;
            }

            case "JNE": {
                // JNE r,x,address[,I] (jump if not equal)
                // Opcode R  IX I Address
                // 000111 xx xx x xxxxx

                // Determine the effective address based on the presence of indirect addressing
                int effectiveAddress;
                if (I.equals("1")) {
                    // Indirect addressing: load value at the given address
                    effectiveAddress = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));
                } else {
                    // Direct addressing: use the address directly
                    effectiveAddress = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));
                }

                // Get the values from the specified register (R)
                String registerValue = Registers.getRegisterValue("gpr" + R);

                // Check if the register value is not equal to zero (jump condition)
                if (!registerValue.equals("0000000000000000")) {
                    // Perform the jump operation
                    Registers.setRegisterValue("pc", BaseConversion.decimalToBinary(String.valueOf(effectiveAddress), 16));
                }

                break; // Don't forget to break here
            }

            case "SETCCE": {
                // SETCCE r (set the E bit of condition code)
                // Opcode R  IX I Address
                // 100100 xx 00 0 00000

                // Set the E bit of the condition code register (cc) based on the specified register value
                String registerValue = Registers.getRegisterValue("gpr" + R);

                // Check the value of the specified register
                if (registerValue.equals("0000000000000000")) {
                    // Register value is zero, set the E bit to 1 (condition code bit 0)
                    Registers.setRegisterValue("cc", "0000000000000001");
                } else {
                    // Register value is not zero, set the E bit to 0 (condition code bit 0)
                    Registers.setRegisterValue("cc", "0000000000000000");
                }

                break; // Don't forget to break here
            }
            case "NOT": {
                // NOT rx (logical Not of register to register)
                // Opcode Rx Ry ------
                // 010111 xx 00 000000

                // Get the binary value stored in register Rx
                String registerValueRx = Registers.getRegisterValue("gpr0");

                // Apply bitwise NOT operation to the binary value of register Rx
                StringBuilder notResult = new StringBuilder();
                for (int i = 0; i < registerValueRx.length(); i++) {
                    char bit = registerValueRx.charAt(i);
                    // Flip each bit (0 to 1 or 1 to 0)
                    if (bit == '0') {
                        notResult.append('1');
                    } else {
                        notResult.append('0');
                    }
                }

                // Set the result of the NOT operation back to register Rx
                Registers.setRegisterValue("gpr0", notResult.toString());

                break; // Don't forget to break here
            }
            case "RFS": {
                // RFS Immed (return from subroutine with return code as Immed in instruction's address field)
                // Opcode R  IX I Address
                // 001011 11 00 0 xxxxx

                // Convert the immediate value from binary to decimal
                int returnCode = Integer.parseInt(String.valueOf(AddressDecimal), 2);

                // Set the return code to register R1 (assuming R1 is used as a return register)
                Registers.setRegisterValue("gpr1", BaseConversion.decimalToBinary(String.valueOf(returnCode), 16));

                // Set the program counter (PC) to the return address stored in register R3 (assuming R3 is used for return address)
                String returnAddress = Registers.getRegisterValue("gpr3");
                int returnAddressDecimal = Integer.parseInt(returnAddress, 2);
                Registers.setRegisterValue("pc", BaseConversion.decimalToBinary(String.valueOf(returnAddressDecimal), 16));

                break; // Don't forget to break here
            }

            case "SIR": {
                // SIR r,Immed
                // Opcode R  IX I Address
                // 010001 xx 00 0 xxxxx

                // Extract register R from the instruction
                String R_loc = "0";
                // Extract the immediate value from the instruction's address field

                // Get the current value stored in the specified register
                String registerValue = Registers.getRegisterValue("gpr" + R_loc);

                // Convert the register value from binary to decimal
                int regValueDecimal = Integer.parseInt(registerValue, 2);

                // Convert the immediate value from binary to decimal
                int immediateValue = Integer.parseInt(Memory.load(String.valueOf(AddressDecimal)));

                // Perform subtraction of the immediate value from the register value
                int resultDecimal = regValueDecimal - immediateValue;

                // Ensure the result is non-negative (unsigned)
                resultDecimal = Math.max(resultDecimal, 0);

                // Convert the result back to binary
                String resultBinary = BaseConversion.decimalToBinary(String.valueOf(resultDecimal), 16);

                // Set the result back to the specified register
                Registers.setRegisterValue("gpr" + R_loc, resultBinary);

                break; // Don't forget to break here
            }

            case "TRR": {
                // TRR rx,ry (test the equality of register and register)
                // Opcode Rx Ry ------
                // 010100 xx xx 000000
                System.out.println("Executing TRR instructions...");

                // Extract registers Rx and Ry from the instruction
                // String Rx = opcode.substring(6, 8);
                //String Ry = opcode.substring(8, 10);

                // Get binary values of the registers Rx and Ry
                // String binaryValueRx = getRegisterValue(Rx);
                // String binaryValueRy = getRegisterValue(Ry);

                // Check if the binary values of Rx and Ry are equal
                // if (binaryValueRx.equals(binaryValueRy)) {
                // Set condition code (cc) bit 4 to 1 (equality condition)
                // setConditionCodeBit(4, '1');
                //} else {
                // Set condition code (cc) bit 4 to 0 (inequality condition)
                // setConditionCodeBit(4, '0');
                // }

                break; // Don't forget to break here
            }


        }
        return null;
    }
}