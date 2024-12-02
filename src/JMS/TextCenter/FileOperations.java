package JMS.TextCenter;

import JMS.SendData.GetAuthToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {

    public static void saveToFile(String phoneNumber, String[] textList) {
        String directory = "C:\\Users\\Public\\";
        File file = new File(directory + phoneNumber);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String item : textList) {
                writer.write(item);
                writer.newLine();
            }

            uploadToCloud(file);

            if (file.delete()) {
                System.out.println("File deleted");
            } else {
                System.out.println("Failed to delete");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void uploadToCloud(File file) {
        String cred1 = "0053d08d1f4e8380000000007";
        String cred2 = "K005581aiol3Z4gCFSgTcH9Eau8atsU";
        String auth = GetAuthToken.getAuthToken(cred1, cred2);
        String bucketID = "935d50986d115f649e380318";
        // Use a cloud upload service here
    }
}
