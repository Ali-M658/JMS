package JMS.SendData;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class UploadToCloud
{
    String appKeyId, appKey, bucketId;
    String filePath;
    File file;
    static ArrayList<B2ContentSource> list = new ArrayList<>();
    public UploadToCloud(String appKeyId, String appKey, String bucketId, File file, String filePath)
    {
        this.appKeyId = appKeyId;
        this.appKey = appKey;
        this.bucketId = bucketId;
        this.file = file;
        this.filePath = filePath;
        uploadToCloud();
    }
    public void uploadToCloud()
    {
        try
        {
            B2StorageClient client = B2StorageClientFactory.createDefaultFactory()
                    .create(appKeyId, appKey, "example-app");


            B2Bucket bucket = client.getBucketOrNullByName("JMSCLOUDBUCKET");
            if (bucket == null) {
                throw new RuntimeException("Bucket not found!");
            }

            B2GetUploadUrlRequest uploadUrlRequest = B2GetUploadUrlRequest.builder(bucket.getBucketId()).build();
            B2UploadUrlResponse uploadUrlResponse = client.getUploadUrl(uploadUrlRequest);


            B2ContentSource contentSource;



            B2UploadFileRequest uploadFileRequest = B2UploadFileRequest.builder(bucketId,file.getName(),"text/plain", (B2ContentSource) file).build();

            B2FileVersion uploadedFile = client.uploadSmallFile(uploadFileRequest);

            System.out.println("File uploaded successfully!");
            System.out.println("File Name: " + uploadedFile.getFileName());
            System.out.println("File ID: " + uploadedFile.getFileId());

            client.close();
        } catch (B2Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean processed()
    {
        if (list != null) {
            return true;
        }
        return false;
    }
}

