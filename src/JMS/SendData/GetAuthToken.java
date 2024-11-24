package JMS.SendData;

import java.io.*;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

public class GetAuthToken
{
    private static ArrayList<String> save = new ArrayList<>();
    public static String getAuthToken(String applicationKeyId, String applicationKey) {
        try {
            // Construct the authorization URL
            String apiUrl = "https://api.backblazeb2.com/b2api/v2/b2_authorize_account";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set the Authorization header with the base64 encoded credentials
            String credentials = applicationKeyId + ":" + applicationKey;
            String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
            System.console().println(encodedCredentials);
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            // Get the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // The response will contain the auth token and other information
            String responseString = response.toString();
            String authToken = parseAuthTokenFromResponse(responseString);
            System.console().writer().write(authToken+"This is authtoken");
            save.add(authToken);
            return authToken;  // Return the extracted auth token

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    private static String parseAuthTokenFromResponse(String response) {
        String authToken = null;
        try {
            // Look for the 'authorizationToken' in the response (simple string search)
            int startIndex = response.indexOf("\"authorizationToken\":") + "\"authorizationToken\":".length();
            int endIndex = response.indexOf("\"", startIndex);
            if (startIndex != -1 && endIndex != -1) {
                authToken = response.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authToken;
    }
    public static boolean processData() {
        if (save.getFirst() != null) {
            System.out.print(save.getFirst());
            System.console().writer().println("Line above this is authtoken");
            return true;
        }
        return false;
    }
}
