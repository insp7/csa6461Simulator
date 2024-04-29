public class BaseConversion {

    // Converts decimal to binary
    public static String decimalToBinary(String decStr, int binStrLen) {
        int decInteger = Integer.parseInt(decStr);
        String binStr = Integer.toBinaryString(decInteger);
        int leadingZerosNum = binStrLen - binStr.length();
        if (leadingZerosNum > 0) {
            binStr = "0".repeat(leadingZerosNum) + binStr;
        }
        return binStr;
    }

    // Converts binary to octal
    public static String binaryToOctal(String binStr, int octStrLen) {
        binStr = binStr.replaceFirst("^0+", ""); // remove leading zeros
        int binStrLen = binStr.length();
        int octStrWithoutLeadingZerosLen = binStrLen / 3 + (binStrLen % 3 != 0 ? 1 : 0);
        int leadingZerosNum = octStrLen - octStrWithoutLeadingZerosLen;
        char[] octCharArr = new char[octStrLen];
        for (int i = 0; i < leadingZerosNum; i++) {
            octCharArr[i] = '0';
        }
        for (int i = 0, j = binStrLen - 1; i < octStrWithoutLeadingZerosLen; i++) {
            int oneDigit = Character.getNumericValue(binStr.charAt(j));
            if (j >= 1) {
                oneDigit += 2 * Character.getNumericValue(binStr.charAt(j - 1));
            }
            if (j >= 2) {
                oneDigit += 4 * Character.getNumericValue(binStr.charAt(j - 2));
            }
            octCharArr[octStrLen - 1 - i] = Character.forDigit(oneDigit, 8);
            j -= 3;
        }
        return new String(octCharArr);
    }

    // Method to convert a hexadecimal digit to its binary representation
    public static String hexDigitToBinary(char hexDigit) {
        int decimalValue = Character.digit(hexDigit, 16);
        String binaryValue = Integer.toBinaryString(decimalValue);
        return String.format("%04d", Integer.parseInt(binaryValue));
    }

    // Method to convert a hexadecimal number to binary
    public static String hexToBinary(String hexNumber) {
        StringBuilder binaryNumber = new StringBuilder();
        for (int i = 0; i < hexNumber.length(); i++) {
            binaryNumber.append(hexDigitToBinary(hexNumber.charAt(i)));
        }
        return binaryNumber.toString();
    }

    // Method to convert an octal digit to its binary representation
    public static String octalDigitToBinary(char octalDigit) {
        int decimalValue = Character.digit(octalDigit, 8);
        String binaryValue = Integer.toBinaryString(decimalValue);
        return String.format("%03d", Integer.parseInt(binaryValue));
    }

    // Method to convert an octal string to binary
    public static String octalToBinary(String octalString) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < octalString.length(); i++) {
            binaryString.append(octalDigitToBinary(octalString.charAt(i)));
        }
        // Ensure the binary string is at most 16 bits
        if (binaryString.length() > 16) {
            binaryString = new StringBuilder(binaryString.substring(binaryString.length() - 16));
        }
        // Pad with leading zeros if necessarys
        while (binaryString.length() < 16) {
            binaryString.insert(0, "0");
        }
        return binaryString.toString();
    }

    // Converts decimal to octal
    public static String decimalToOctal(int x, int octStrLen) {
        String octStr = Integer.toOctalString(x);
        int leadingZerosNum = octStrLen - octStr.length();
        if (leadingZerosNum > 0) {
            octStr = "0".repeat(leadingZerosNum) + octStr;
        }
        return octStr;
    }

    // Function to convert Octal to Decimal
    public static int octalToDecimal(String octalStr) {
        int decimal = 0;
        int power = 0;

        // Loop through each digit of the Octal number from right to left
        for (int i = octalStr.length() - 1; i >= 0; i--) {
            // Convert char to integer
            int digit = Character.getNumericValue(octalStr.charAt(i));

            // Multiply the digit with 8 raised to the power of its position
            decimal += (int) (digit * Math.pow(8, power));

            // Move to the next position
            power++;
        }

        return decimal;
    }

    // Function to convert Decimal to 6-digit Octal
    public static String decimalTo6DigitOctal(int decimal) {
        // Convert decimal to octal
        String octal = Integer.toOctalString(decimal);

        // Pad with leading zeros if necessary to make it 6 digits
        while (octal.length() < 6) {
            octal = "0" + octal;
        }

        return octal;
    }

    // Function to convert Binary to Decimal
    public static int binaryToDecimal(String binaryStr) {
        int decimal = 0;

        // Loop through each digit of the Binary number from left to right
        for (int i = 0; i < binaryStr.length(); i++) {
            // Convert char to integer
            int digit = Character.getNumericValue(binaryStr.charAt(i));

            // Multiply the digit with 2 raised to the power of its position from the right
            decimal += digit * Math.pow(2, binaryStr.length() - 1 - i);
        }

        return decimal;
    }
}
