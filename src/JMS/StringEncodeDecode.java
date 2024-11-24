package JMS;

public class StringEncodeDecode {
    // Custom encoding algorithm
    public static String encode(String input, String offsett) {
        int offset = Integer.parseInt(offsett);
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Shift each character by the offset
            encoded.append((char) (c + offset));
        }
        return encoded.toString();
    }

    // Custom decoding algorithm
    public static String decode(String encoded, String offsett) {
        int offset = Integer.parseInt(offsett);
        StringBuilder decoded = new StringBuilder();
        for (char c : encoded.toCharArray()) {
            // Reverse the shift to get the original character
            decoded.append((char) (c - offset));
        }
        return decoded.toString();
    }
}


