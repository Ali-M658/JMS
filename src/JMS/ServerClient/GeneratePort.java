package JMS.ServerClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class GeneratePort {

    public static int getSharedPortForConversation(String[] phoneNumbers)
    {
        try {

            Arrays.sort(phoneNumbers);

            StringBuilder combinedString = new StringBuilder();
            for (String phoneNumber : phoneNumbers) {
                combinedString.append(phoneNumber);
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(combinedString.toString().getBytes());

            long hashValue = 0;
            for (int i = 0; i < hashBytes.length; i++) {
                hashValue = (hashValue << 8) | (hashBytes[i] & 0xFF);  // Combine bytes into a long
            }

            int port = (int) (hashValue % 1000) + 5000;
            return port;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
