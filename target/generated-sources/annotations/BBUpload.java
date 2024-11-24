package JMS.SendData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.*;

public class BBUpload
{
    public BBUpload(String appKeyID, String bucketId)
    {
        try
        {
            System.console().writer().println("BBUpload is put!");
            String apiUrl = "https://api.backblazeb2.com/b2api/v2/b2_upload_file";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Authorization", "Bearer " + authToken);

            //JSON
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonBody = String.format(
                    "{\"bucketId\": \"%s\", \"fileName\": \"%s\"}",
                    bucketId,
                    fileName
            );

            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream())
            {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null)
                    {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println("File uploaded!");
                }
                else
                {
                    System.out.println("Error uploading file. Response code: " + responseCode);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
