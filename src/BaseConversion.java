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

    // Converts decimal to octal
    public static String decimalToOctal(int x, int octStrLen) {
        String octStr = Integer.toOctalString(x);
        int leadingZerosNum = octStrLen - octStr.length();
        if (leadingZerosNum > 0) {
            octStr = "0".repeat(leadingZerosNum) + octStr;
        }
        return octStr;
    }
}
