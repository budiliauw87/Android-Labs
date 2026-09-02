package dev.liau.nfc;

public class Utils {
    static String toHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            // Use String.format to ensure 2 characters per byte with leading zeros
            sb.append(String.format("%02X", bytes[i]));
            if (i < bytes.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    static String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; i--) {
            sb.append(String.format("%02X", bytes[i]));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    static long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (byte b : bytes) {
            long value = b & 255;
            result += value * factor;
            factor *= 256;
        }
        return result;
    }

    static long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; i--) {
            long value = bytes[i] & 255;
            result += value * factor;
            factor *= 256;
        }
        return result;
    }

     static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
